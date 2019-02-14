package changhua.com.myflickclient.ui.main;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import changhua.com.myflickclient.databinding.RowLayoutBinding;
import changhua.com.myflickclient.model.PhotoItem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<PhotoItem> items;

    private ItemClickListener mClickListener;

    MyAdapter(List<PhotoItem> items) {
        this.items = items;
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RowLayoutBinding binding;

        MyViewHolder(RowLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(this);
        }

        void bind(PhotoItem item) {

            binding.setPhotoRow(item);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    class PhotoItemDiffCallback extends DiffUtil.Callback {

        private List<PhotoItem> mOldList;
        private List<PhotoItem> mNewList;

        PhotoItemDiffCallback(List<PhotoItem> oldList, List<PhotoItem> newList) {
            this.mOldList = oldList;
            this.mNewList = newList;
        }

        @Override
        public int getOldListSize() {
            return mOldList != null ? mOldList.size() : 0;
        }

        @Override
        public int getNewListSize() {
            return mNewList != null ? mNewList.size() : 0;
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            String oldId = mOldList.get(oldItemPosition).imageUrl;
            String newId = mNewList.get(newItemPosition).imageUrl;
            return Objects.equals(oldId, newId);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            PhotoItem oldPhotoItem = mOldList.get(oldItemPosition);
            PhotoItem newPhotoItem = mNewList.get(newItemPosition);
            return Objects.equals(oldPhotoItem, newPhotoItem);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowLayoutBinding binding = RowLayoutBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PhotoItem repo = items.get(position);
        holder.bind(repo);

        if (position == items.size() - 1) {
            Log.d("MyAdapter", "Last row is reached!");
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    void swapItems(List<PhotoItem> newItems) {
        if (newItems == null) {
            int oldSize = this.items.size();
            this.items.clear();
            notifyItemRangeRemoved(0, oldSize);
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new PhotoItemDiffCallback(this.items, newItems));
            this.items.clear();
            this.items.addAll(newItems);
            result.dispatchUpdatesTo(this);
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}


