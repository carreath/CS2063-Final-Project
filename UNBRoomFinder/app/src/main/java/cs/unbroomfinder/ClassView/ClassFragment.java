package cs.unbroomfinder.ClassView;

/**
 * Created by kylebrideau on 2017-02-18.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import cs.unbroomfinder.Course;
import cs.unbroomfinder.R;


public class ClassFragment extends Fragment implements View.OnClickListener {
    private boolean mTwoPane = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.class_tabs, container, false);

        Button btn = (Button) rootView.findViewById(R.id.btn_add_class);
        btn.setOnClickListener(this);

        if(rootView.findViewById(R.id.class_list_recycle) != null) {
            mTwoPane = true;

            LinkedList<Course> list = new LinkedList<Course>();

            // TODO: add a query to select all courses
            list.add(new Course("ABC", 1));
            list.add(new Course("sfgsdABsdfC", 2));
            list.add(new Course("123A123B344ffC", 3));
            list.add(new Course("123A12B123C", 4));
            list.add(new Course("ABC2421", 5));
            list.add(new Course("AfgdfhherC", 6));
            list.add(new Course("sdfsfC", 7));

            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.class_list_recycle);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);

            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(list));
        }


        return rootView;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), AddClassActivity.class);
        startActivity(intent);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Course> mValues;

        public SimpleItemRecyclerViewAdapter(List<Course> data) {
            mValues = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.course_item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.course = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getName());
            // TODO: change this from FK to name
            holder.mContentView.setText(mValues.get(position).getRmNumber() + "");
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * Setting the data to be sent to the Detail portion of the template.
                     * Here, we send the title, longitude, and latitude of the Earthquake
                     * that was clicked in the RecyclerView. The Detail Activity/Fragment
                     * will then display this information. Condition check is whether we
                     * are twoPane on a Tablet, which varies how we pass arguments to the
                     * participating activity/fragment.
                     */
                    String name = holder.course.getName();
                    int roomNumber = holder.course.getRmNumber();

                    Context context = v.getContext();
                    Intent intent = new Intent(context, CourseSettingActivity.class);
                    intent.putExtra(CourseSettingActivity.COURSE_NAME, name);
                    intent.putExtra(CourseSettingActivity.ROOM_NUMBER, "" + roomNumber);

                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Course course;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
