package com.task.my.task.adapter;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.task.my.task.database.DBOpenHelper;
import com.task.my.task.MainActivity;
import com.task.my.task.R;
import com.task.my.task.database.TableFields;
import com.task.my.task.fragment.TextDialog;

import java.util.List;

import static com.task.my.task.MainActivity.year;

//TODO: Remove

public class XRecyclerAdapter extends RecyclerView.Adapter<XRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<Integer> list;
    private int month;
    private LayoutInflater mInflater;
    private FragmentManager manager;
    private Boolean active;


    public XRecyclerAdapter(Context context, List<Integer> dayList, int month, FragmentManager manager) {

        this.mContext = context;
        this.list = dayList;
        this.month = month;
        this.mInflater = LayoutInflater.from(context);
        this.manager = manager;
        active = false;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.recycler_text, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final int day = list.get(holder.getAdapterPosition());

        holder.setDate(list.get(position));

        if (day == 1 && !active) {
            active = true;
        } else if (day == 1) {
            active = false;
        }

        if (active) {

            DBOpenHelper helper = new DBOpenHelper(mContext);
            SQLiteDatabase db = helper.getReadableDatabase();

            int count = (int) DatabaseUtils.queryNumEntries(db, TableFields.EventEntry.TABLE_NAME,
                    TableFields.EventEntry.EVENT_CREATED + " = ? and " + TableFields.EventEntry.EVENT_CHECKED + " = 1",
                    new String[]{year + "-" + month + "-" + day});
//        Check for row existence
            int rowExists = (int) DatabaseUtils.queryNumEntries(db, TableFields.EventEntry.TABLE_NAME,
                    TableFields.EventEntry.EVENT_CREATED + " = ?",
                    new String[]{year + "-" + month + "-" + day});


            if (count > 3) {
                holder.mTextView.setBackgroundResource(R.drawable.ic_flag_win);

            } else if (rowExists > 0) {
                holder.mTextView.setBackgroundResource(R.drawable.ic_flag_loss);
            }

            db.close();

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MainActivity.day = day;

                    TextDialog dialog = new TextDialog();
                    dialog.setCancelable(false);
                    dialog.show(manager, null);

                }
            });
        } else {
            holder.mTextView.setTextColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public View view;

        public ViewHolder(View view) {
            super(view);

            mTextView = (TextView) view.findViewById(R.id.myText);
            this.view = view;

        }

        public void setDate(int date) {

            mTextView.setText(date + "");

        }
    }
}
