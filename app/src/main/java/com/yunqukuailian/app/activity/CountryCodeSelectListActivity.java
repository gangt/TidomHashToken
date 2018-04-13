package com.yunqukuailian.app.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseActivity;
import com.yunqukuailian.app.model.CountryCode.CharacterParser;
import com.yunqukuailian.app.model.CountryCode.CityLetterSortAdapter;
import com.yunqukuailian.app.model.CountryCode.LetterSideBar;
import com.yunqukuailian.app.model.CountryCode.PinyinComparator;
import com.yunqukuailian.app.model.CountryCode.SortModel;
import com.yunqukuailian.app.model.CountryCode.StickyListHeadersListView;
import com.yunqukuailian.app.model.EventBusEvent.RegisterCountryCodeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryCodeSelectListActivity extends BaseActivity implements
        StickyListHeadersListView.OnHeaderClickListener, AdapterView.OnItemClickListener
        , StickyListHeadersListView.OnLoadingMoreLinstener {

    CityLetterSortAdapter mAdapter;
    StickyListHeadersListView stickyLV;
    private static final String TAG = CountryCodeSelectListActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;

    private CharacterParser mCharacterParser;
    private List<SortModel> sourceDateFilterList = new ArrayList<SortModel>();
    private EditText searchEt;
    private List<SortModel> sourceDateList;


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.setTitleBar(this, toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        mainfragment4title.setText("区号");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_city_select;
    }

    public void initView() {
        mCharacterParser = CharacterParser.getInstance();
        PinyinComparator pinyinComparator = new PinyinComparator();
        sourceDateList = filledData(getResources().getStringArray(R.array.cities_data));
        Collections.sort(sourceDateList, pinyinComparator);

        mAdapter = new CityLetterSortAdapter(this, sourceDateList);
        stickyLV = (StickyListHeadersListView) this.findViewById(R.id.stickyList);
        stickyLV.setAdapter(mAdapter);
        stickyLV.setOnItemClickListener(this);
        stickyLV.setOnHeaderClickListener(this);
        stickyLV.setLoadingMoreListener(this);
        LetterSideBar letterSideBar = (LetterSideBar) findViewById(R.id.cs_letter_sb);
        letterSideBar.setOnTouchingLetterChangedListener(
                new LetterSideBar.OnTouchingLetterChangedListener() {
                    @Override
                    public void onTouchingLetterChanged(String letter) {
//                        Logger.d(TAG, "onTouchingLetterChanged  letter: " + letter);
                        int jumpPos = mAdapter.getPositionForSection(letter.charAt(0));
                        stickyLV.setSelection(jumpPos);
                    }
                });
        letterSideBar.setTextView((TextView) findViewById(R.id.cs_selected_letter_tv));

      /*  searchEt = (EditText) findViewById(R.id.cs_search_et);
        searchEt.setVisibility(View.VISIBLE);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 0) {
                    mAdapter.updateListView(sourceDateList);
                    mAdapter.notifyDataSetChanged();
                } else {
                    String searchText = searchEt.getText().toString();
                    if (TextUtils.isEmpty(searchText)) {
//                        SingleToast.show(getApplicationContext(), R.string.msg_keyword_is_empty);
                    }
                    sourceDateFilterList.clear();
                    for (int h = 0; h < sourceDateList.size(); h++) {
                        if (sourceDateList.get(h).getName().contains(searchText)) {
                            sourceDateFilterList.add(sourceDateList.get(h));
                        }
                    }

                    if (sourceDateFilterList.size() <= 0) {
                        //                    noFilterPhoneFriends();
                    } else {
                        mAdapter.updateListView(sourceDateFilterList);
                        mAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
    }

    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0, n = date.length; i < n; i++) {
            /**
             *    String country = name.substring(0, name.indexOf("+"));
             String tvName_code  = name.substring(name.indexOf("+"));
             */
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i].substring(0, date[i].indexOf("+")));
            sortModel.setName_code(date[i].substring(date[i].indexOf("+")));

            String pinyin = mCharacterParser.getSelling(date[i]);
            sortModel.setPinyin(pinyin);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetter(sortString.toUpperCase());
            } else {
                sortModel.setSortLetter("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;
    }

    @Override
    public void OnLoadingMore() {
    }

    @Override
    public void onHeaderClick(StickyListHeadersListView l, View header,
                              int itemPosition, long headerId, boolean currentlySticky) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String city = ((SortModel) mAdapter.getItem(position)).getName();
        String code = ((SortModel) mAdapter.getItem(position)).getName_code();
        // USE EventBus
//        setResult(RESULT_OK, getIntent().putExtra("extra_city", city));
        EventBus.getDefault().post(new RegisterCountryCodeEvent(city,code));
        finish();
    }
}