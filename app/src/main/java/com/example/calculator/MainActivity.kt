package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var tvInput: TextView? = null
    var lastNumeric = false
    var lastDot =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput =findViewById(R.id.tv_input)
    }
    fun onDigitClick(view:View){
        tvInput?.append( (view as Button).text)
        lastNumeric=true
        lastDot=false
    }

    fun onClear(view:View){
        tvInput?.text = ""
        lastNumeric=false
        lastDot=false
    }
    fun onDecimalClicked(view:View){
       if (lastNumeric && !lastDot){
           tvInput?.append(".")
           lastNumeric=false
           lastDot=true
       }
    }

    fun onOperator (view:View){
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorContain(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDot=false
            }
        }
    }
    fun isOperatorContain(value: String): Boolean{
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/")||  value.contains("*")|| value.contains("+")|| value.contains("-")
        }
    }

    fun onEqual(view:View){
        if (lastNumeric && !lastDot){
            var value = tvInput?.text.toString()
            var prefix = ""
            try {
                if (value.startsWith("-")){
                    prefix ="-"
                    value =value.substring(1)
                }
                if (value.contains("-")){
                    var splitValue = value.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (value.contains("+")){
                    var splitValue = value.split("+")
                    var one = splitValue[0]

                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (value.contains("/")){
                    var splitValue = value.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if (value.contains("*")){
                    var splitValue = value.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    fun removeZeroAfterDot(result:String):String{
        var value= result
        if(value.contains(".0")){
            value =result.substring(0,result.length-2)
        }
        return  value
    }
}