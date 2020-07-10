package com.example.myfirebasedatabasecodinginflow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<MyProfile>
{
    private Activity context;
    private List<MyProfile> myProfileList;

    public CustomListAdapter(Activity context, List<MyProfile> myProfileList)
    {
        super(context,R.layout.custom_list_view,myProfileList);
        this.context = context;
        this.myProfileList = myProfileList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.custom_list_view,null,true);

        TextView firstName = listViewItem.findViewById(R.id.firstName);
        TextView surName = listViewItem.findViewById(R.id.surName);
        TextView city = listViewItem.findViewById(R.id.city);

        MyProfile myProfile = myProfileList.get(position);

        firstName.setText(myProfile.getName());
        surName.setText(myProfile.getSurname());
        city.setText(myProfile.getCity());

        return listViewItem;
    }
}
