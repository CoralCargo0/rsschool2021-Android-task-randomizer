package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.random.Random
import kotlin.random.nextInt

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        val generatedResult = generate(min, max)
        result?.text = generatedResult.toString()

        backButton?.setOnClickListener {
            // TODO: implement back
            MainActivity().openFirstFragment(generatedResult)

        }
    }

    private fun generate(min: Int, max: Int): Int {
        // TODO: generate random number
        val random =  Random.nextInt(min..max)          //(min..max).random()
//        numListener.onGenerateRandom(random)
        return random
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()

            args.putInt(SecondFragment.MIN_VALUE_KEY, min)
            args.putInt(SecondFragment.MAX_VALUE_KEY, max)
            // TODO: implement adding arguments
            fragment.arguments = args
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}