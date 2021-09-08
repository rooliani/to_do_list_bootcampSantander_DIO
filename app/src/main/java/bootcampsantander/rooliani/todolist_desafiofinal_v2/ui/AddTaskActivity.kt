package bootcampsantander.rooliani.todolist_desafiofinal_v2.ui

import android.app.Activity
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import bootcampsantander.rooliani.todolist_desafiofinal_v2.R
import bootcampsantander.rooliani.todolist_desafiofinal_v2.model.Task
import bootcampsantander.rooliani.todolist_desafiofinal_v2.datasource.TaskDataSource
import bootcampsantander.rooliani.todolist_desafiofinal_v2.databinding.ActivityAddTaskBinding
import bootcampsantander.rooliani.todolist_desafiofinal_v2.extensions.format
import bootcampsantander.rooliani.todolist_desafiofinal_v2.extensions.text
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity:AppCompatActivity (){
    private lateinit var binding : ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)                                    //LayoutInflater vem direto da Activity / e pode ser utilizado graças ao AppCompactActivity
        setContentView(binding.root)

        if(intent.hasExtra(TASK_ID)){                                                               // só irá receber o Extra quando abrir p EDIT se for ADD não terá
            val taskId = intent.getIntExtra(TASK_ID,0)
            TaskDataSource.findById(taskId)?.let {
                binding.tilTitle.text= it.title
                binding.tilDate.text=it.date
                binding.tilHour.text=it.hour
                binding.btnNewTask.text = getString(R.string.label_edit_task)
            }
        }

        insertListeners()
    }

    private fun insertListeners() {

        binding.tilDate.editText?.setOnClickListener{

            val datePicker= MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener {
                //corrigir a zona para data correta
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1

                binding.tilDate.text =  Date(it + offset).format()
            }

            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")                          // a tag serve para localizar o ID da Data Picker
        }

        binding.tilHour.editText?.setOnClickListener {

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                binding.tilHour.text = String.format("%02d:%02d", timePicker.hour,timePicker.minute) // fiz diferente do professor
            }
            timePicker.show(supportFragmentManager, null)
        }

        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text,
                date = binding.tilDate.text,
                hour = binding.tilHour.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )
            TaskDataSource.insertTask(task)

            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    companion object{
        const val TASK_ID = "task_id"
    }

}