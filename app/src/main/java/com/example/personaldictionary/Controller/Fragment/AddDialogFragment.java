package com.example.personaldictionary.Controller.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personaldictionary.Database.AppDatabase;
import com.example.personaldictionary.Model.Word;
import com.example.personaldictionary.R;


public class AddDialogFragment extends DialogFragment {
    private EditText mEditTextTitle;
    private EditText mEditTextMeaning;
    private Button mButtonAdd;
    private TextView mTextViewAdd;
    private AppDatabase appDatabase;

    public static AddDialogFragment newInstance() {
        AddDialogFragment fragment = new AddDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDatabase = AppDatabase.getInstance(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_add_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        findViews(view);
        initView();
        setListeners();
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.editText_title_in_add_dialog);
        mEditTextMeaning = view.findViewById(R.id.editText_meaning_in_add_dialog);
        mButtonAdd = view.findViewById(R.id.button_add_in_add_dialog);
        mTextViewAdd = view.findViewById(R.id.textView_title_in_add_dialog);
    }

    private void initView(){
        if(WordsListFragment.LANGUAGE_FLAG == false){
            mTextViewAdd.setText("ADD");
            mButtonAdd.setText("Add");
            mEditTextTitle.setHint("ENTER WORD");
            mEditTextMeaning.setHint("ENTER MEANING");
        } else {
            mTextViewAdd.setText("افزودن");
            mButtonAdd.setText("اضافه شود");
            mEditTextTitle.setHint("لغت را وارد کنید");
            mEditTextMeaning.setHint("معنی را وارد کنید");
        }
    }
    private void setListeners(){
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditTextTitle.length() == 0 || mEditTextMeaning.length() == 0)
                    Toast.makeText(getActivity(), "Fill the blanks!", Toast.LENGTH_LONG).show();
                else {
                    Word word = new Word(mEditTextTitle.getText().toString(), mEditTextMeaning.getText().toString());
                    appDatabase.mIWordDatabaseDao().insertWord(word);
                    getTargetFragment().onActivityResult(WordsListFragment.ADD_DIALOG_FRAGMENT_REQUEST_CODE,
                            Activity.RESULT_OK, null);
                    dismiss();
                }

            }
        });
    }
}