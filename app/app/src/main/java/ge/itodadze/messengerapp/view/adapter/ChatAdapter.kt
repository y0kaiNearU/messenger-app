package ge.itodadze.messengerapp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.itodadze.messengerapp.R
import ge.itodadze.messengerapp.databinding.ChatReceivedBinding
import ge.itodadze.messengerapp.databinding.ChatSentBinding
import ge.itodadze.messengerapp.utils.toHoursMinutes
import ge.itodadze.messengerapp.viewmodel.models.Message

class ChatAdapter(private val context: Context, private var messages: List<Message>, private val userId: String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            SENT -> {
                SentMessageViewHolder(ChatSentBinding.inflate(
                    LayoutInflater.from(parent.context)))
            }
            else -> {
                ReceivedMessageViewHolder(ChatReceivedBinding.inflate(
                    LayoutInflater.from(parent.context)))
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SentMessageViewHolder -> {
                val viewHolder: SentMessageViewHolder = holder
                viewHolder.message.text = messages[position].text
                viewHolder.time.text = messages[position].timestamp?.toHoursMinutes()
            }
            is ReceivedMessageViewHolder -> {
                val viewHolder: ReceivedMessageViewHolder = holder
                viewHolder.message.text = messages[position].text
                viewHolder.time.text = messages[position].timestamp?.toHoursMinutes()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].sender_id.equals(userId)) {
            SENT
        } else {
            RECEIVED
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(newMessages: List<Message>?) {
        messages = newMessages ?: emptyList()
        notifyDataSetChanged()
    }

    fun getTopSpacing(position: Int): Int {
        return if (position == 0 || messages[position].sender_id
                .equals(messages[position - 1].sender_id)) {
           context.resources.getDimensionPixelOffset(R.dimen.chat_same_person_vertical_margin)
        } else {
            context.resources.getDimensionPixelOffset(R.dimen.chat_different_person_vertical_margin)
        }
    }

    companion object {
        private const val SENT: Int = 1
        private const val RECEIVED: Int = 2
    }

}

class SentMessageViewHolder(val binding: ChatSentBinding): RecyclerView.ViewHolder(binding.root) {
    val message: TextView = binding.text
    val time: TextView = binding.time
}

class ReceivedMessageViewHolder(val binding: ChatReceivedBinding): RecyclerView.ViewHolder(binding.root) {
    val message: TextView = binding.text
    val time: TextView = binding.time
}