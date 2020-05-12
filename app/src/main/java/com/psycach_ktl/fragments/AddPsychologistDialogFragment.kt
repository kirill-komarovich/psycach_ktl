package com.psycach_ktl.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.psycach_ktl.R

class AddPsychologistDialogFragment(val onPositive: (email: String) -> Unit) : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.add_psychologist_dialog_fragment, null)

        val dialog = MaterialAlertDialogBuilder(requireActivity())
            .setView(view)
            .setTitle(R.string.add_psychologist_form_title)
            .setNeutralButton(resources.getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(resources.getString(R.string.add)) { _, _ ->
                val emailInput: TextInputLayout = view.findViewById(R.id.add_psychologist_email_input)
                val email = emailInput.editText!!.text.toString()
                onPositive(email)
            }
            .create()

        dialog.window?.setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        return dialog
    }
}
