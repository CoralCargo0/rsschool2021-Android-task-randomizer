package com.rsschool.android2021

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        val minEditText = view.findViewById<EditText>(R.id.min_value)
        val maxEditText = view.findViewById<EditText>(R.id.max_value)


        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = resources.getString(R.string.previousResultText, result)


        var min: Int? =
            if (MainActivity.min == -1) null else MainActivity.min
        var max: Int? =
            if (MainActivity.max ==-1) null else MainActivity.max

        min?.let {
            minEditText.setText(it.toString())
        }
        max?.let {
            maxEditText.setText(it.toString())
        }


        minEditText.doAfterTextChanged {
            minEditText.text?.run {
                if (isEmpty()) {
                    min = null
                } else {
                    toString().toLongOrNull()?.let {
                        if( it <= Int.MAX_VALUE) min = it.toInt()
                        else {
                            snackMessage(" MIN > int max value, try again")
                            min = null
                            minEditText.setText("")
                        }

                    }
                }
            }
        }

        maxEditText.doAfterTextChanged {
            maxEditText.text?.run {
                if (isEmpty()) {
                    max = null
                } else {
                    toString().toLongOrNull()?.let {
                        if(it <= Int.MAX_VALUE) max = it.toInt()
                        else {
                            snackMessage(" MAX > int max value, try again")
                            max = null
                            maxEditText.setText("")
                        }
                    }
                }
            }
        }

        generateButton?.setOnClickListener {
            when {

                min == null && max == null -> {
                    snackMessage("All fields are empty")
                    vibro()
                }
                min == null -> {
                    snackMessage("Min field is empty")
                    vibro()
                }
                max == null -> {
                    snackMessage("Max field is empty")
                    vibro()
                }
                min!! >= max!! -> snackMessage(" MIN >= MAX ")
                else -> {
                    generateListener().minMaxSaver(min!!, max!!)
                    mainActivity().openSecondFragment(min!!, max!!)
                }

            }
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun vibro() {
        val vibrator = this.activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val canVibrate: Boolean = vibrator.hasVibrator()
        val milliseconds = 10L

        if (canVibrate) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    milliseconds,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
    }

    private fun snackMessage(charSequence: CharSequence) = view?.let {
        hideKeyboard()
        Snackbar.make(it, charSequence, Snackbar.LENGTH_SHORT).apply {
            view.findViewById<TextView>(R.id.snackbar_text).apply {
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                textSize = 20F
            }
            setAction("HIDE") {}
        }.show()
    }

}