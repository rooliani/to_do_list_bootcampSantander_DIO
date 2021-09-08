package bootcampsantander.rooliani.todolist_desafiofinal_v2.datasource

import bootcampsantander.rooliani.todolist_desafiofinal_v2.model.Task

object TaskDataSource{

    private val list = arrayListOf<Task>()

    fun getList() = list.toList()                                                                   //mudou de list >>> list.toList() para criar um nova referência e evitar o problema de atualização da Reclycle View

    fun insertTask(task: Task){
        if(task.id == 0){
            list.add(task.copy(id=list.size + 1))
        }else{
            list.remove(task)
            list.add(task)
        }

    }

    fun findById(taskId: Int) = list.find { it.id == taskId }                                       // se encontrar vai retornar verdadeiro


    fun deleteTask(task: Task) {
        list.remove(task)
    }


}