package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class FactoryAdapter<E> extends BaseAdapter {
    protected List<E> items;
    protected LayoutInflater inflater;

    public FactoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }

    public FactoryAdapter(Context context, List<E> items) {
        inflater = LayoutInflater.from(context);
        this.items = items;
    }

    public void add(E item) {
        if (item == null) return;
        this.items.add(item);
        notifyDataSetChanged();
    }

    public void add(E[] arrays) {
        if (arrays == null || arrays.length <= 0) return;
        for (int i = 0; i < arrays.length; i++) {
            this.items.add(arrays[i]);
        }
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends E> items) {
        if (items == null) return;
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public E getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderFactory<E> holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(getLayoutResourceId(), parent, false);
            holder = createFactory(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderFactory<E>) convertView.getTag();
        }
        holder.init(getItem(position), position, this);
        Log.d("TAG", "getView: " + position);
        return convertView;
    }

    protected abstract ViewHolderFactory<E> createFactory(View view);

    public abstract int getLayoutResourceId();

    public interface ViewHolderFactory<E> {
        void init(E item, int position, FactoryAdapter<E> adapter);
    }

    public List<E> getItems(){
        return items;
    }
}
