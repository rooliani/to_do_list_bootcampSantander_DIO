package bootcampsantander.rooliani.todolist_desafiofinal_v2.ui

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import bootcampsantander.rooliani.todolist_desafiofinal_v2.R
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
        insertListeners()
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("Tela 2: ","onRestart " + hasTasks().toString())


    }
    override fun onResume() {
        super.onResume()
        Log.e("Tela 2: ","onResume")
    }


    override fun onPause() {
        super.onPause()
        Log.e("Tela 2: ","onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.e("Tela 2: ","onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("Tela 2: ","onDestroy")
    }
    override fun onBackPressed() {
        super.onBackPressed()
        Log.e("Tela 2: ","onBackPressed")
    }

    /**
     * startActivityForResult DEPRECIADO > VERSÃO ATUALIZADA
     */
    private fun insertListeners() {

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
            Log.e("Tela 2: ","DELETAR " + hasTasks().toString())
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


    private fun hasTasks():Boolean{
        val list = TaskDataSource.getList()
        return  list.isNotEmpty()
    }

    private fun updateList(){
        //val list = TaskDataSource.getList()

       // if(list.isEmpty()){
        if(hasTasks()){
            binding.includeEmptyState.emptyState.visibility = View.GONE
            binding.layoutMain.setBackgroundColor(getResources().getColor(THEME_TASKS_COLOR_BACKGROUND))
            getWindow().navigationBarColor = getResources().getColor(THEME_TASKS_COLOR_NAVIGATION_BAR)
            binding.fabAdd.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(THEME_TASKS_COLOR_ADD)))
        }else{

            binding.includeEmptyState.emptyState.visibility = View.VISIBLE
            binding.layoutMain.setBackgroundColor(getResources().getColor(THEME_NO_TASKS_COLOR_BACKGROUND))
            getWindow().navigationBarColor = getResources().getColor(THEME_NO_TASKS_COLOR_NAVIGATION_BAR)
            binding.fabAdd.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(THEME_NO_TASKS_COLOR_ADD)))
        }
        adapter.submitList(TaskDataSource.getList())



        //2 -  forma diferente de escrever o if
        /*
        binding.includeEmptyState.emptyState.visibility =
            if(list.isEmpty()) View.VISIBLE
            else View.GONE
         */
    }

    companion object{

        private const val CREATE_NEW_TASK = 1000

        private const val THEME_TASKS_COLOR_BACKGROUND = R.color.laranja
        private const val THEME_TASKS_COLOR_NAVIGATION_BAR = R.color.azul_escuro2
        private const val THEME_TASKS_COLOR_ADD = R.color.azul_escuro1

        private const val THEME_NO_TASKS_COLOR_BACKGROUND = R.color.azul_escuro1
        private const val THEME_NO_TASKS_COLOR_NAVIGATION_BAR = R.color.laranja
        private const val THEME_NO_TASKS_COLOR_ADD = R.color.azul_claro1
    }
}