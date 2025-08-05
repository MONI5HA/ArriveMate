// ui/news/NewsAdapter.kt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.arivemate.data.model.News
import com.example.arivemate.databinding.NewsItemBinding

class NewsAdapter(
    private val articles: List<News>,
    private val onItemClick: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.binding.newtitleText.text = article.title
        holder.binding.newsDateText.text = article.pubDate
        Glide.with(holder.itemView.context).load(article.image_url).into(holder.binding.newImageview)
        holder.binding.newDescriptionText.text = article.description
        holder.binding.root.setOnClickListener {
            onItemClick(article)
        }
    }
}
