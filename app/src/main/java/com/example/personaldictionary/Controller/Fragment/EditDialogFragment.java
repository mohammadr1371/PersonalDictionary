package com.example.personaldictionary.Controller.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personaldictionary.Database.AppDatabase;
import com.example.personaldictionary.Model.Word;
import com.example.personaldictionary.R;


public class EditDialogFragment extends DialogFragment {

    public static final String WORD_IN_EDIT_FRAGMENT = "word in edit fragment";
    private AppDatabase mAppDatabase;
    private Word mWord;

    private EditText mEditTextTitle;
    private EditText mEditTextMeaning;
    private Button mButtonDone;
    private Button mButtonCancel;

    public static EditDialogFragment newInstance(Word word) {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(WORD_IN_EDIT_FRAGMENT, word);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWord = (Word) getArguments().getSerializable(WORD_IN_EDIT_FRAGMENT);
        mAppDatabase = AppDatabase.getInstance(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_edit_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        findViews(view);
        initView();
        setListeners();
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.editText_title_in_edit_dialog);
        mEditTextMeaning = view.findViewById(R.id.editText_meaning_in_edit_dialog);
        mButtonDone = view.findViewById(R.id.button_done_in_edit_dialog);
        mButtonCancel = view.findViewById(R.id.button_cancel_in_edit_dialog);
    }

    private void initView() {
        mEditTextTitle.setText(mWord.getTitle());
        mEditTextMeaning.setText(mWord.getMeaning());
    }

    private void setListeners() {
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mEditTextTitle.getText().toString();
                String meaning = mEditTextMeaning.getText().toString();
                if (title.length() == 0 || meaning.length() == 0){
                    Toast.makeText(getActivity(), "Fill the blanks", Toast.LENGTH_SHORT).show();
                } else {
                    mWord.setTitle(title);
                    mWord.setMeaning(meaning);
                    mAppDatabase.mIWordDatabaseDao().updateWordTable(mWord);
                    getTargetFragment().onActivityResult(WordsListFragment.EDIT_DIALOG_FRAGMENT_REQUEST_CODE
                    , Activity.RESULT_OK, null);
                    dismiss();
                }
            }
        });
    }
}