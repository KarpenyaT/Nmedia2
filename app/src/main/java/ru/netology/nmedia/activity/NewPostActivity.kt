package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.edit.setText(intent.getStringExtra(Intent.EXTRA_TEXT)) //запись текста intent в поле edit активити
        binding.ok.setOnClickListener { //при нажатии +
            val text = binding.edit.text.toString()
            if (text.isNotBlank()) {
                setResult(RESULT_OK, Intent().apply { putExtra(KEY_TEXT, text) })
            } else {
                setResult(RESULT_CANCELED)
            }
            finish() //выход из активити всегда
        }
    }

    companion object {
        const val KEY_TEXT = "post_text"
    }
}

object NewPostContract : ActivityResultContract<String?, String?>() {
    override fun createIntent(context: Context, input: String?): Intent {
        val intent = Intent(context, NewPostActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, input)
        return intent
    }

    //возравт данных из этой activity в mainActivity
    override fun parseResult(resultCode: Int, intent: Intent?) =
        intent?.getStringExtra(NewPostActivity.KEY_TEXT)

}