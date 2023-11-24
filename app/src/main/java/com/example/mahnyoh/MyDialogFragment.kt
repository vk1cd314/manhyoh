package com.example.mahnyoh


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment

class MyDialogFragment : DialogFragment() {
    private var mNum: Int = 0

    companion object {
        fun newInstance(num: Int): MyDialogFragment {
            val fragment = MyDialogFragment()
            val args = Bundle()
            args.putInt("num", num)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNum = arguments?.getInt("num") ?: 0

        // Pick a style based on the num.
        var style = STYLE_NORMAL
        var theme = 0
        when ((mNum - 1) % 6) {
            1 -> style = STYLE_NO_TITLE
            2 -> style = STYLE_NO_FRAME
            3 -> style = STYLE_NO_INPUT
            4, 5 -> style = STYLE_NORMAL
            6, 7 -> style = STYLE_NO_TITLE
            8 -> style = STYLE_NORMAL
        }
        when ((mNum - 1) % 6) {
            4 -> theme = android.R.style.Theme_Holo
            5 -> theme = android.R.style.Theme_Holo_Light_Dialog
            6, 8 -> theme = android.R.style.Theme_Holo_Light
            7 -> theme = android.R.style.Theme_Holo_Light_Panel
        }
        setStyle(style, theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set DialogFragment title
        dialog?.setTitle("Dialog #$mNum")
    }
}
