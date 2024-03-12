import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post

val empty= Post(
    id=0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
    shareByMe = false,
)
class PostViewModel() : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited=MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id:Long)=repository.removeById(id)
    fun changeContentAndSave(content:String){
        edited.value?.let {
            if (content!=it.content){
                repository.save(it.copy(content = content))
            }
            edited.value=empty
        }
    }
    fun  editById(post: Post) {
        edited.value=post
    }

    fun cancelEdit(){
        edited.value=empty
    }
}