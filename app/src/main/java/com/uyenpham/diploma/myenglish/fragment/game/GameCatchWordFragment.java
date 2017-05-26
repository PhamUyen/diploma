package com.uyenpham.diploma.myenglish.fragment.game;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.fragment.BaseFragment;
import com.uyenpham.diploma.myenglish.model.Vocabulary;
import com.uyenpham.diploma.myenglish.utils.Const;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by Ka on 4/6/2017.
 */

public class GameCatchWordFragment extends BaseFragment implements AnswerFrame.OnAnswerResultListener{
    @BindView(R.id.img_question)
    ImageView imgQuestion;
    @BindView(R.id.ln_img)
    LinearLayout lnImage;
    @BindView(R.id.fr_answer)
    AnswerFrame answerFrame;
    @BindView(R.id.tv_quest)
    TextView tvQuest;

    private ArrayList<Vocabulary> listAllWord;
    @Override
    protected int getLayoutId() {
        return R.layout.fragemnt_game;
    }

    @Override
    protected void createView(View view) {
        getExtra();
        generateQuestion();
        answerFrame.setResultListener(this);
    }
    private void getExtra(){
        Bundle bundle = getArguments();
        listAllWord =bundle.getParcelableArrayList(Const.KEY_LIST_WORD);
    }

    private synchronized void generateQuestion() {
        int index = new Random().nextInt(listAllWord.size()-1);
        Vocabulary word = listAllWord.get(index);
        AnswerFrame.Question question = new AnswerFrame.Question(word.getWord(), word.getWord());
        ItemQuestion itemQuestion;
        if(new Random().nextBoolean()){
            itemQuestion =  new ItemQuestion("What do you see?", word.getImage(), question);
            Glide.with(this).load(itemQuestion.getImageUrl()).asBitmap().into(imgQuestion);
        }else {
            itemQuestion =  new ItemQuestion("What do you listen?", Const.GOOGLE_VOCABULARY.replace("hello",word.getWord()), question);
            Glide.with(this).load(R.drawable.icon_loa).into(imgQuestion);
            MediaPlayer mediaPlayer= new MediaPlayer();
            try {
                mediaPlayer.setDataSource(mActivity,Uri.parse(itemQuestion.getImageUrl()));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tvQuest.setText(itemQuestion.getQuest());
        answerFrame.generateQuestion(itemQuestion.getQuestion());
    }
    private void showCorrectAnswer() {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.thach_do_traloidung);
//        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
//        bitmapDrawable.setCornerRadius(cornerRadium);
//        FlipAnimator flipAnimator = new FlipAnimator(imgQuestion);
//        flipAnimator.setToDrawable(bitmapDrawable);
//        flipAnimator.setDuration(300);
//        imgQuestion.startAnimation(flipAnimator);

        answerFrame.resetEvent();
//        btnContinue.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnAnswerResult(boolean result) {
        if (result) {
            showCorrectAnswer();
        } else {
            //TODO handle wrong answer at here.
            Vibrator vibrator = (Vibrator) mActivity.getSystemService(Context.VIBRATOR_SERVICE);
            // delay 0,first time vibrate for 500 milliseconds, next time delay 50 milliseconds,
            // and post that vibrate 500 milliseconds.
            long[] pattern = {0, 500, 50, 500};
            vibrator.vibrate(pattern, -1);
        }
    }

    @Override
    public void OnFinishGenerateAnswer(List<TextView> generationViews) {
        Animation topDown = AnimationUtils.loadAnimation(mActivity,
                R.anim.slide_in_bottom_daily);
        lnImage.startAnimation(topDown);
        Animation bottomUp = AnimationUtils.loadAnimation(mActivity,
                R.anim.slide_in_up_daily);
        for (TextView textView : generationViews) {
            textView.startAnimation(bottomUp);
        }
    }

    // Fake class for generate question.
    public static class ItemQuestion {
        String imageUrl;
        AnswerFrame.Question question;
        String quest;

        public ItemQuestion(String quest, String imageUrl, AnswerFrame.Question question) {
            this.quest = quest;
            this.imageUrl = imageUrl;
            this.question = question;
        }

        public String getQuest() {
            return quest;
        }

        public void setQuest(String quest) {
            this.quest = quest;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public AnswerFrame.Question getQuestion() {
            return question;
        }

        public void setQuestion(AnswerFrame.Question question) {
            this.question = question;
        }
    }
}
