package jbankz.com;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jbankz.com.model.pojo;

public class ScrollingActivity extends AppCompatActivity {

    private ImageView mDetailsImage;
    private TextView mTextView, mDate;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.scrolling_toolbar);
        setSupportActionBar(toolbar);

        Context context = getApplicationContext();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDetailsImage = (ImageView) findViewById(R.id.details_image);
        mTextView = (TextView) findViewById(R.id.detail_news);
        mDate = (TextView) findViewById(R.id.date);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        pojo pojo = getIntent().getParcelableExtra("data");

        String imageUrl = pojo.getUrlToImage();

        String author = pojo.getAuthor();

        if (author != null) {
            collapsingToolbarLayout.setTitle(author);
        } else {
            collapsingToolbarLayout.setTitle("CNN NEWS");
        }

        mTextView.append(pojo.getTitle() + "\n\n"
                + pojo.getDescription());

        mDate.setText(pojo.getPublishedAt());

        Picasso.with(context).load(imageUrl).placeholder(R.drawable.placeholder).into(mDetailsImage);

    }
}
