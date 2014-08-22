package com.cocomsys.gmaps101;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Programador on 21/08/2014.
 */
public class CcListDialog extends Dialog {

    ListView lvContent;
    TextView tvTitle;
    String mTitle;
    String[] mItems;
    AdapterView.OnItemClickListener mItemClickListener;
    OnCcItemClickListener mListItemClickListener;

    public CcListDialog(Context context) {
        super(context);
    }

    public CcListDialog(Context context, String [] items) {
        super(context);
        this.mItems = items;
        build(items);
    }

    public CcListDialog(Context context,
                        String mTitle, String[] mItems,
                        AdapterView.OnItemClickListener mItemClickListener) {
        super(context);
        this.mTitle = mTitle;
        this.mItems = mItems;
        this.mItemClickListener = mItemClickListener;
        build();
    }

    public CcListDialog(Context context,
                        String mTitle, String[] mItems,
                        OnCcItemClickListener itemClickListener) {
        super(context);
        this.mTitle = mTitle;
        this.mItems = mItems;
        this.mListItemClickListener = itemClickListener;
        build(mTitle, mItems, mListItemClickListener);
    }

    protected CcListDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setData(String title, OnCcItemClickListener itemClickListener){
        setDialogTitle(title);
        setDialogListListener(itemClickListener);
    }

    public void setDialogTitle(String title){
        this.mTitle = title;
        if(!TextUtils.isEmpty(title))
            tvTitle.setText(title);
    }

    public void setDialogListListener(final OnCcItemClickListener itemClickListener){
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemClickListener.onItemClick(i);
                dismiss();
            }
        });
    }

    private void build(String title, String[] items){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cc_list_dialog_layout);

        lvContent = (ListView)findViewById(R.id.dialog_lv_content);
        tvTitle = (TextView)findViewById(R.id.dialog_tv_title);

        setDialogTitle(title);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_list_item_1, items);
        lvContent.setAdapter(adapter);
    }

    private void build(String[] items){
        build("", items);
    }

    private void build(String title, String[] items, AdapterView.OnItemClickListener itemClickListener){
        build(title, items);
        lvContent.setOnItemClickListener(itemClickListener);
    }

    private void build(String title, String[] items, final OnCcItemClickListener itemClickListener){
        build(title, items);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemClickListener.onItemClick(i);
                dismiss();
            }
        });
    }

    private void build(){
        build(mTitle, mItems, mItemClickListener);
    }

    public CcListDialog build(Context ctx, String title, String[] items,
                              AdapterView.OnItemClickListener itemClickListener){
        CcListDialog dialog = new CcListDialog(ctx, title, items, itemClickListener);
        build();
        return dialog;
    }

    public CcListDialog build(Context ctx, String[] items){
        CcListDialog dialog = new CcListDialog(ctx, items);
        build(items);
        return dialog;
    }

    public interface OnCcItemClickListener {
        void onItemClick(int position);
    }

}
