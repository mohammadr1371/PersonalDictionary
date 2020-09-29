package com.example.personaldictionary.Controller.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personaldictionary.Database.AppDatabase;
import com.example.personaldictionary.Database.iWordDatabaseDao;
import com.example.personaldictionary.Model.Word;
import com.example.personaldictionary.R;

import java.util.ArrayList;
import java.util.List;


public class WordsListFragment extends Fragment {

    public static final String ADD_DIALOG_FRAGMENT = "add dialog fragment";
    public static final String SEARCH_DIALOG_FRAGMENT = "search dialog fragment";
    public static final int ADD_DIALOG_FRAGMENT_REQUEST_CODE = 1;
    public static final int EDIT_DIALOG_FRAGMENT_REQUEST_CODE = 2;
    public static final String EDIT_DIALOG_FRAGMENT = "edit dialog fragment";
    public static boolean LANGUAGE_FLAG = false;

    private iWordDatabaseDao mIWordDatabaseDao;
    private RecyclerView mRecyclerView;
    private Button mButtonLanguage;
    private TextView mTextViewAppTitle;
    private Menu mMenu;


    public static WordsListFragment newInstance() {
        WordsListFragment fragment = new WordsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIWordDatabaseDao = AppDatabase.getInstance(getActivity()).mIWordDatabaseDao();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_words_list, container, false);
        findViews(view);
        initView();
        setListener();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateList();
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(String.valueOf(mIWordDatabaseDao.getWordsList().size())+" Words");
        WordAdapter wordAdapter = new WordAdapter(mIWordDatabaseDao.getWordsList());
        mRecyclerView.setAdapter(wordAdapter);
    }

    public void findViews(View view){
        mRecyclerView = view.findViewById(R.id.word_recycler_view);
        mButtonLanguage = view.findViewById(R.id.button_language);
        mTextViewAppTitle = view.findViewById(R.id.textView_app_title);
    }

    public void initView(){
        if(LANGUAGE_FLAG == false){
            mTextViewAppTitle.setText("Raeis Dictionary");
            mButtonLanguage.setText("En");
        } else {
            mTextViewAppTitle.setText("دیکشنری رئیس");
            mButtonLanguage.setText("Pr");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_CANCELED){
            return;
        }

        if(requestCode == ADD_DIALOG_FRAGMENT_REQUEST_CODE){
            updateList();
            return;
        }

        if(requestCode == EDIT_DIALOG_FRAGMENT_REQUEST_CODE){
            updateList();
            return;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_layout, menu);
        mMenu = menu;
        initMenu(menu);
    }

    private void initMenu(@NonNull Menu menu) {
        if(LANGUAGE_FLAG == false) {
            menu.findItem(R.id.add_item_in_menu).setTitle("Add");
            menu.findItem(R.id.search_item_in_menu).setTitle("Search");
            menu.findItem(R.id.delete_item_in_menu).setTitle("Delete all");
        } else {
            menu.findItem(R.id.add_item_in_menu).setTitle("افزودن");
            menu.findItem(R.id.search_item_in_menu).setTitle("جستجو");
            menu.findItem(R.id.delete_item_in_menu).setTitle("پاک کردن همه");

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item_in_menu:
                AddDialogFragment addDialogFragment = AddDialogFragment.newInstance();
                addDialogFragment.setTargetFragment(WordsListFragment.this, ADD_DIALOG_FRAGMENT_REQUEST_CODE);
                addDialogFragment.show(getFragmentManager(), ADD_DIALOG_FRAGMENT);
                break;
            case R.id.search_item_in_menu:
                SearchDialogFragment searchDialogFragment = SearchDialogFragment.newInstance((ArrayList<Word>) mIWordDatabaseDao.getWordsList());
                searchDialogFragment.show(getFragmentManager(), SEARCH_DIALOG_FRAGMENT);
                break;
            case R.id.delete_item_in_menu:
                if(mIWordDatabaseDao.getWordsList().size()==0){
                    Toast.makeText(getActivity(), "No item exist", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Are you sure to delete all of words?")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mIWordDatabaseDao.deleteALL();
                                    updateList();
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    break;
                }

            default:
            return super.onOptionsItemSelected(item);
        }
        return false;
    }

    private void setListener(){
        mButtonLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if(LANGUAGE_FLAG == false){
                    LANGUAGE_FLAG = true;
                } else {
                    LANGUAGE_FLAG = false;
                }
                initView();
                initMenu(mMenu);
            }
        });
    }

    private class WordViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView meaning;
        private Button share;
        private Button delete;
        private Button edit;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title_in_row);
            meaning = itemView.findViewById(R.id.textView_meaning_in_row);
            share = itemView.findViewById(R.id.button_share_in_row);
            delete = itemView.findViewById(R.id.button_delete_in_row);
            edit = itemView.findViewById(R.id.button_edit_in_row);
        }

        public void bindRecyclerView (final Word word, int position){
            title.setText(word.getTitle());
            meaning.setText(word.getMeaning());
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, "word: "+word.getTitle()+" meaning is: "+word.getMeaning());
                    intent.setType("text/plain");
                    startActivity(intent);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Are you sure to delete this words?")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mIWordDatabaseDao.deleteWord(word);
                                    updateList();
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditDialogFragment editDialogFragment = EditDialogFragment.newInstance(word);
                    editDialogFragment.setTargetFragment(WordsListFragment.this,
                            EDIT_DIALOG_FRAGMENT_REQUEST_CODE);
                    editDialogFragment.show(getFragmentManager(), EDIT_DIALOG_FRAGMENT);
                }
            });
        }
    }

    private class WordAdapter extends RecyclerView.Adapter<WordViewHolder> {
        List<Word> mWords;

        public WordAdapter(List<Word> words) {
            mWords = words;
        }

        @NonNull
        @Override
        public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.row_layout, parent, false);
            WordViewHolder wordViewHolder = new WordViewHolder(view);
            return wordViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
            holder.bindRecyclerView(mWords.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mWords.size();
        }
    }



}