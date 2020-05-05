import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';

admin.initializeApp();

const USERS_COLLECTION = 'users';
const firestore = admin.firestore()

export const userCreated = functions.auth.user().onCreate((user) => {
  // TODO: find if exist
  return firestore.collection(USERS_COLLECTION).doc(user.uid).set({
    email: user.email,
    displayName: user.displayName,
    active: true,
  })
})

export const userDeleted = functions.auth.user().onCreate((user) => {
  return firestore.collection(USERS_COLLECTION).doc(user.uid).set({ active: false }, { merge: true })
})

export const addPsychologist = functions.https.onCall(async (data, context) => {
  const auth = context.auth;
  if (!auth || auth.uid === null) return { error: 'UNAUTHORIZED' }

  const usersCollection = firestore.collection(USERS_COLLECTION)
  const snapshot = await usersCollection
    .where('email', '==', data.email)
    .where('role', '==', 'PSYCHOLOGIST')
    .get()

  if(snapshot.size === 0) return;

  const batch = firestore.batch();

  const psychologist = snapshot.docs[0]
  const currentUserProfile = await usersCollection.doc(auth.uid).get()

  const currentUserProfileRef = usersCollection.doc(psychologist.id).collection('clients').doc(currentUserProfile.id)
  batch.set(currentUserProfileRef, userDocToData(currentUserProfile.data()))

  const psychologistRef =  usersCollection.doc(currentUserProfile.id).collection('psychologists').doc(psychologist.id)
  batch.set(psychologistRef, userDocToData(psychologist.data()))
  return batch.commit()
})

export const removePsychologist = functions.https.onCall((data, context) => {
  const auth = context.auth;
  if (!auth || auth.uid === null) return { error: 'UNAUTHORIZED' }

  const usersCollection = firestore.collection(USERS_COLLECTION)
  const psychologistRef = usersCollection.doc(data.id)
  const currentUserProfileRef = usersCollection.doc(data.id)

  return firestore.runTransaction(async (transaction) => {
    const psychologist = await transaction.get(psychologistRef)
    const currentUserProfile = await transaction.get(currentUserProfileRef)

    transaction.delete(psychologistRef.collection('clients').doc(currentUserProfile.id))
    transaction.delete(currentUserProfileRef.collection('psychologists').doc(psychologist.id))
  })
})

const userDocToData = (userDoc: any) => {
  return {
    email: userDoc.email,
    displayName: userDoc.displayName,
  }
}
