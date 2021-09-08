package bootcampsantander.rooliani.todolist_desafiofinal_v2.extensions

import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*


private val locale = Locale("pt", "BR")

fun Date.format() : String{
   return SimpleDateFormat("dd/MM/yyyy", locale).format(this)
}

var TextInputLayout.text : String

get()=editText?.text?.toString() ?: ""                                                             //se estiver null, retorna vazio
set(value) { editText?.setText(value)}