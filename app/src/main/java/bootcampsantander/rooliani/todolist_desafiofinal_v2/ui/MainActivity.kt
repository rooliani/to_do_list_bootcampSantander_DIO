package bootcampsantander.rooliani.todolist_desafiofinal_v2.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import bootcampsantander.rooliani.todolist_desafiofinal_v2.databinding.ActivityMainBinding
import bootcampsantander.rooliani.todolist_desafiofinal_v2.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {TaskListAdapter()}

    /**
     * NOVA FORMA de iniciar uma activity
     * pois o startActivityRsult foi depreciado
     * >>> PROF. atualizou no GITHUB
     * >>> video aula desatualizada
     */
    private val register =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK) updateList()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTask.adapter = adapter
        updateList()

        inserListeners()
    }

    /**
     * startActivityForResult DEPRECIADO > VERSÃO ATUALIZADA
     */
    private fun inserListeners() {

        binding.fabAdd.setOnClickListener {
            register.launch(Intent(this, AddTaskActivity::class.java))
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            register.launch(intent)

        }
        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }

    }

    //versão DESATUALIZADA - startActivityForResult
    /*
    private fun inserListeners() {

        binding.fabAdd.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK) //vai criar a activity e devolver um resultado
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)

        }
        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)}
            updateList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()                        // adicionou o "&& resultCode == Activity.RESULT_OK", pois mesmo quando o usuário cliclava em CANCELAR ele estava atualizando a lista

    }*/

    private fun updateList(){
        val list = TaskDataSource.getList()

        //1 -
        /*
        if(list.isEmpty()){
            binding.includeEmptyState.emptyState.visibility = View.VISIBLE
        }else{
            binding.includeEmptyState.emptyState.visibility = View.GONE
        }
        adapter.submitList(TaskDataSource.getList())
        */

        //2 -  forma diferente de escrever o if
        binding.includeEmptyState.emptyState.visibility =
            if(list.isEmpty()) View.VISIBLE
            else  View.GONE

        adapter.submitList(list)

    }

    companion object{
        private const val CREATE_NEW_TASK = 1000
    }
}