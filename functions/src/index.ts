import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';

admin.initializeApp();

export const userCreated = functions.auth.user().onCreate((user) => {
  // TODO: find if exist
  return admin.firestore().collection('users').doc(user.uid).set({
    email: user.email,
    displayName: user.displayName,
    active: true,
  })
})

export const userDeleted = functions.auth.user().onCreate((user) => {
  return admin.firestore().collection('users').doc(user.uid).set({ active: false }, { merge: true })
})
