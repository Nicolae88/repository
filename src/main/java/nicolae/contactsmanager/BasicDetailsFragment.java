package nicolae.contactsmanager;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BasicDetailsFragment extends Fragment {
    Button bSH;
    Button bSave;
    Button bCancel;
    EditText etName;
    EditText etNr;
    EditText etEmail;
    EditText etAddress;
    EditText etJT;
    EditText etCompany;
    EditText etWebsite;
    EditText etIM;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_basic_details,container,false);
        final View view22 = inflater.inflate(R.layout.fragment_additional_details,container,false);
        bSH = (Button) view.findViewById(R.id.button);
        bSave = (Button) view.findViewById(R.id.button2);
        bCancel = (Button) view.findViewById(R.id.button3);

        bSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bSH.getText().toString().equalsIgnoreCase("Show Additional Fields")){
                    bSH.setText("Hide Additional Fields");
                }else{
                    bSH.setText("Show Additional Fields");
                }
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AdditionalDetailsFragment additionalDetailsFragment = (AdditionalDetailsFragment)fragmentManager.findFragmentById(R.id.fragment2);
                if (additionalDetailsFragment == null) {
                    fragmentTransaction.add(R.id.fragment2, new AdditionalDetailsFragment());
                    ((Button)v).setText("Hide Additional Fields");
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);

                } else {
                    fragmentTransaction.remove(additionalDetailsFragment);
                    ((Button)v).setText("Show Additional Fields");
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
                }
                fragmentTransaction.commit();
            }
        });
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName = (EditText) view.findViewById(R.id.editText3);
                etNr = (EditText) view.findViewById(R.id.editText4);
                etEmail = (EditText) view.findViewById(R.id.editText5);
                etAddress = (EditText) view.findViewById(R.id.editText6);

                String name=etName.getText().toString();
                String phone=etNr.getText().toString();
                String email=etEmail.getText().toString();
                String address=etAddress.getText().toString();

                Log.d("ttt",name);

                String jobTitle = null, company=null, website = null, im=null;

                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AdditionalDetailsFragment additionalDetailsFragment = (AdditionalDetailsFragment)fragmentManager.findFragmentById(R.id.fragment2);
                if (additionalDetailsFragment != null) {
                    etJT = (EditText) getActivity().findViewById(R.id.editText7);
                    etCompany = (EditText) getActivity().findViewById(R.id.editText8);
                    etWebsite = (EditText) getActivity().findViewById(R.id.editText9);
                    etIM = (EditText) getActivity().findViewById(R.id.editText10);

                    jobTitle = etJT.getText().toString();
                    company = etCompany.getText().toString();
                    website = etWebsite.getText().toString();
                    im = etIM.getText().toString();

                    Log.d("ttt", jobTitle);

                }
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (name != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }
                if (phone != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }
                if (email != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }
                if (address != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }
                if (jobTitle != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                }
                if (company != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (website != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                    contactData.add(websiteRow);
                }
                if (im != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                getActivity().startActivity(intent);
            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }
}
