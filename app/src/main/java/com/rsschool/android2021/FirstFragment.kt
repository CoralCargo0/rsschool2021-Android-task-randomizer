package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        var minEditText = view.findViewById<EditText>(R.id.min_value)
        var maxEditText = view.findViewById<EditText>(R.id.max_value)


        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        // TODO: val min = ...
        // TODO: val max = ...
        var min: Int? = null
        var max: Int? = null

        minEditText.doAfterTextChanged {
            minEditText.text?.run {
                if (isEmpty()) {
                    min = null
                } else {
                    toString().toIntOrNull()?.let {
                        min = it.toInt()
                    }
                }
            }
        }

        maxEditText.doAfterTextChanged {
            maxEditText.text?.run {
                if (isEmpty()) {
                    max = null
                } else {
                    toString().toIntOrNull()?.let {
                        max = it.toInt()
                    }
                }
            }
        }






        generateButton?.setOnClickListener {
            // TODO: send min and max to the SecondFragment
            if(min != null && max != null && min!! <= max!!) MainActivity().openSecondFragment(min!!, max!!)
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
}