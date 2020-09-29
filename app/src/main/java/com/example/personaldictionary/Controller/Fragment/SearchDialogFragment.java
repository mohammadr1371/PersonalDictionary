package com.example.personaldictionary.Controller.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personaldictionary.Model.Word;
import com.example.personaldictionary.R;

import java.util.ArrayList;


public class SearchDialogFragment extends DialogFragment {

    public static final String WORD_LIST = "word list";
    private EditText mEditTextTitle;
    private ImageButton mImageButtonSearch;
    private RecyclerView mRecyclerViewSearch;
    private Button mButtonBack;
    private TextView mTextViewSearchTitle;
    private ArrayList<Word> mWords;

    public static SearchDialogFragment newInstance(ArrayList<Word> words) {
        SearchDialogFragment fragment = new SearchDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(WORD_LIST, words);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWords = (ArrayList<Word>) getArguments().getSerializable(SearchDialogFragment.WORD_LIST);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_search_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        findViews(view);
        initView();
        setListeners();
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void findViews(View view){
        mEditTextTitle = view.findViewById(R.id.editText_in_search_dialog);
        mImageButtonSearch = view.findViewById(R.id.imageButton_search_in_search_dialog);
        mRecyclerViewSearch = view.findViewById(R.id.recyclerView_in_search_dialog);
        mButtonBack = view.findViewById(R.id.button_back_in_search_dialog);
        mTextViewSearchTitle = view.findViewById(R.id.textView_search_title);
    }

    private void initView(){
        if(WordsListFragment.LANGUAGE_FLAG == false){
            mTextViewSearchTitle.setText("SEARCH");
            mButtonBack.setText("Back");
        } else {
            mTextViewSearchTitle.setText("جستجو");
            mButtonBack.setText("بازگشت");
        }
    }

    private void setListeners(){
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mImageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Word> resultList = new ArrayList<>();
                String inputWord = mEditTextTitle.getText().toString();
                if(inputWord.length()==0){
                    Toast.makeText(getActivity(), "Please enter a word", Toast.LENGTH_LONG).show();
                } else {
                    resultList = search(inputWord);
                    if(resultList.size()==0){
                        Toast.makeText(getActivity(), "No item found", Toast.LENGTH_LONG).show();
                    } else {
                        mRecyclerViewSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
                        SearchedWordAdapter searchedWordAdapter = new SearchedWordAdapter(resultList);
                        mRecyclerViewSearch.setAdapter(searchedWordAdapter);
                    }
                }
            }
        });
    }

    private ArrayList<Word> search (String inputTitle){
        ArrayList<Word> outputWordList = new ArrayList<>();
        for (Word word: mWords) {
            if(word.getTitle().contains(inputTitle)){
                outputWordList.add(word);
            }
        }
        return outputWordList;
    }

    private class SearchedWordViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewTitle;
        private TextView mTextViewMeaning;

        public SearchedWordViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.textView_title_in_searched_row);
            mTextViewMeaning = itemView.findViewById(R.id.textView_meaning_in_searched_row);
        }
    }

    private class SearchedWordAdapter extends RecyclerView.Adapter<SearchedWordViewHolder>{
        ArrayList<Word> mWordArrayList = new ArrayList<>();

        public SearchedWordAdapter(ArrayList<Word> wordArrayList) {
            mWordArrayList = wordArrayList;
        }

        @NonNull
        @Override
        public SearchedWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.searched_word_row, parent, false);
            SearchedWordViewHolder searchedWordViewHolder = new SearchedWordViewHolder(view);
            return searchedWordViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SearchedWordViewHolder holder, int position) {
            holder.mTextViewTitle.setText(mWordArrayList.get(position).getTitle());
            holder.mTextViewMeaning.setText(mWordArrayList.get(position).getMeaning());
        }

        @Override
        public int getItemCount() {
            return mWordArrayList.size();
        }
    }
}