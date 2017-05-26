package com.uyenpham.diploma.myenglish.fragment.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uyenpham.diploma.myenglish.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AnswerFrame extends FrameLayout {

    public interface OnAnswerResultListener {
        void OnAnswerResult(boolean result);

        void OnFinishGenerateAnswer(List<TextView> generationViews);
    }

    private static final int MAX_CHARACTER_OF_ROW = 8;
    private static final int GENERATE_TOP_ID = 10000;
    private static final int GENERATE_BOTTOM_ID = 11000;
    private int viewSize = 0;
    private int margin = 25;

    private Question question;
    private ArrayList<TextView> answerViews;
    private ArrayList<TextView> generationViews;
    private LinearLayout lnBackGroundGenerationAnswer;
    private ArrayList<String> choiceAlphaAnswers;
    private ArrayList<Integer> choicePositions;
    private OnAnswerResultListener resultListener;

    public AnswerFrame(final Context context) {
        this(context, null, 0);
    }

    public AnswerFrame(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnswerFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Display display = ((WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);
        int deviceWidth = deviceDisplay.x;
        viewSize = (deviceWidth - margin * (MAX_CHARACTER_OF_ROW + 1)) / MAX_CHARACTER_OF_ROW;
        answerViews = new ArrayList<>();
        generationViews = new ArrayList<>();
        choiceAlphaAnswers = new ArrayList<>();
        choicePositions = new ArrayList<>();
    }

    public void setResultListener(OnAnswerResultListener resultListener) {
        this.resultListener = resultListener;
    }

    /**
     * generate content of answer UI.
     *
     * @param question it is answer characters.
     */
    public synchronized void generateQuestion(Question question) {
        this.removeAllViews();
        answerViews.clear();
        choiceAlphaAnswers.clear();
        choicePositions.clear();
        choiceAlphaAnswers.clear();
        lnBackGroundGenerationAnswer = null;
        this.question = null;

        String fontPath = getContext().getString(R.string.Baloo_Regular);
        Typeface face = Typeface.createFromAsset(getContext().getAssets(), fontPath);
        float fontSize = 18;

        this.question = question;
        List<String> generateLetters = question.makeGenerateAnswer();
        int answerLength = question.getAlphaChars().size();
        int startIndex = 0;
        if (answerLength <= MAX_CHARACTER_OF_ROW) {
            startIndex = MAX_CHARACTER_OF_ROW - answerLength;
        }
        boolean isCheck = false;

        LinearLayout linearLayoutTop = new LinearLayout(getContext());
        linearLayoutTop.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout linearLayoutBottom = new LinearLayout(getContext());
        linearLayoutBottom.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams paramsLayoutTop;
        LinearLayout.LayoutParams paramsLayoutBototm;
        if (answerLength >= MAX_CHARACTER_OF_ROW) {
            paramsLayoutTop = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayoutTop.setLayoutParams(paramsLayoutTop);
            linearLayoutTop.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayoutTop.setPadding(margin, 0, 0, 0);

            paramsLayoutBototm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayoutBottom.setLayoutParams(paramsLayoutBototm);
            int numberOfTopChar = MAX_CHARACTER_OF_ROW;
            int numberOfBottomChar = answerLength - MAX_CHARACTER_OF_ROW;
            int paddingLeft = (numberOfTopChar * viewSize + (numberOfTopChar + 1) * margin - numberOfBottomChar * viewSize - (numberOfBottomChar - 1) * margin) / 2;
            linearLayoutBottom.setPadding(paddingLeft, viewSize, 0, 0);

        } else {
            paramsLayoutTop = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayoutTop.setLayoutParams(paramsLayoutTop);
            int paddingLeft = (MAX_CHARACTER_OF_ROW * viewSize + (MAX_CHARACTER_OF_ROW + 1) * margin - answerLength * viewSize - (answerLength - 1) * margin) / 2;
            linearLayoutTop.setPadding(paddingLeft, 0, 0, 0);
            linearLayoutBottom = new LinearLayout(getContext());

        }
        //linearLayoutBottom.setGravity(Gravity.CENTER_HORIZONTAL);

        addView(linearLayoutTop);
        addView(linearLayoutBottom);
        for (int i = 0; i < answerLength; i++) {
            TextView tvAnswer = new TextView(getContext());
            tvAnswer.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(viewSize, viewSize,
                    Gravity.TOP);
            if (i < MAX_CHARACTER_OF_ROW) {
                params.setMargins(0, 0, margin, 0);
                startIndex++;
                tvAnswer.setOnClickListener(listener);
                tvAnswer.setGravity(Gravity.CENTER);
                tvAnswer.setId(GENERATE_TOP_ID + i);
                tvAnswer.setBackgroundResource(R.drawable.bg_empty_letter);
                tvAnswer.setTypeface(face);
                tvAnswer.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                tvAnswer.setLayoutParams(params);
                answerViews.add(tvAnswer);
                linearLayoutTop.addView(tvAnswer);
            } else {
                if (!isCheck) {
                    // second row.
                    startIndex = (MAX_CHARACTER_OF_ROW * 2 - (i + 1)) / 2 - 2;
                    isCheck = true;
                }
                params.setMargins(0, margin, margin, 0);
                startIndex++;
                tvAnswer.setOnClickListener(listener);
                tvAnswer.setGravity(Gravity.CENTER);
                tvAnswer.setId(GENERATE_TOP_ID + i);
                tvAnswer.setBackgroundResource(R.drawable.bg_empty_letter);
                tvAnswer.setTypeface(face);
                tvAnswer.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                tvAnswer.setLayoutParams(params);
                answerViews.add(tvAnswer);
                linearLayoutBottom.addView(tvAnswer);
            }


        }


        //Add background
        lnBackGroundGenerationAnswer = new LinearLayout(getContext());
        LayoutParams param = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (viewSize * 2 + margin*3), Gravity.BOTTOM);
        lnBackGroundGenerationAnswer.setBackgroundColor(getResources()
                .getColor(R.color.bg_add_question));
        this.addView(lnBackGroundGenerationAnswer, param);

        for (int i = 0; i < generateLetters.size(); i++) {
            TextView tvGenerate = new TextView(getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(viewSize, viewSize, Gravity.BOTTOM);
            if (i < MAX_CHARACTER_OF_ROW) {
                params.setMargins(margin + ((viewSize + margin) * i), 0, 0, viewSize + (margin * 2));
            } else {
                params.setMargins(margin + ((viewSize + margin) * (i - MAX_CHARACTER_OF_ROW)), 0, 0, margin);
            }

            tvGenerate.setOnClickListener(listener);
            tvGenerate.setGravity(Gravity.CENTER);
            tvGenerate.setPadding(0, 0, 0, 0);
            tvGenerate.setId(GENERATE_BOTTOM_ID + i);
            tvGenerate.setBackgroundResource(R.drawable.bg_answer_letter);
            String letter = generateLetters.get(i);
            tvGenerate.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            tvGenerate.setTextColor(getResources().getColor(R.color.color_black));
            tvGenerate.setTypeface(face);
            tvGenerate.setText(letter);
            tvGenerate.setLayoutParams(params);
            generationViews.add(tvGenerate);
            this.addView(tvGenerate);
        }
        if (resultListener != null) {
            resultListener.OnFinishGenerateAnswer(generationViews);
        }
    }

    /**
     * remove answer events.
     */
    public void resetEvent() {
        if (answerViews != null && answerViews.size() > 0) {
            for (TextView tvAnswer : answerViews) {
                tvAnswer.setOnClickListener(null);
            }
        }
        if (lnBackGroundGenerationAnswer != null) {
            lnBackGroundGenerationAnswer.setVisibility(GONE);
        }
        if (generationViews != null && generationViews.size() > 0) {
            for (TextView tvAnswer : generationViews) {
                tvAnswer.setVisibility(GONE);
                tvAnswer.setOnClickListener(null);
            }
        }

    }

    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {

            TextView tvClick = (TextView) view;
            if (view.getId() < GENERATE_BOTTOM_ID) {
                Property property = (Property) view.getTag();
                if (property != null) {
                    TextView tvMove = property.getTvMove();
                    view.setTag(null);
                    int indexRemove = 0;
                    for (int i = 0; i < answerViews.size(); i++) {
                        TextView textView = answerViews.get(i);
                        if (tvClick.getId() == textView.getId()) {
                            for (int j = 0; j < choicePositions.size(); j++) {
                                if (choicePositions.get(j) == i) {
                                    indexRemove = j;
                                }
                            }
                            if (choicePositions.size() > indexRemove) {
                                choicePositions.remove(indexRemove);
                            }
                        }
                    }
                    moveToGenerate((TextView) view, tvMove);
                }
            } else {
                for (int i = 0; i < answerViews.size(); i++) {
                    TextView textView = answerViews.get(i);
                    if (textView.getTag() == null) {
                        choicePositions.add(i);
                        moveToAnswer(tvClick, textView);
                        break;
                    }
                }
            }
        }
    };

    /**
     * checking answer characters fill to full.
     *
     * @return
     */
    private boolean detectingFillAnswer() {
        choiceAlphaAnswers.clear();
        for (int i = 0; i < answerViews.size(); i++) {
            for (int j = 0; j < choicePositions.size(); j++) {
                if (choicePositions.get(j) == i) {
                    choiceAlphaAnswers.add(answerViews.get(i).getText().toString());
                    break;
                }
            }
        }
        return choiceAlphaAnswers.size() == answerViews.size();
    }

    /**
     * Move view to answer area.
     *
     * @param viewOne this view need to move.
     * @param viewTwo this view target that one moving to it.
     */
    private synchronized void moveToAnswer(final TextView viewOne, final TextView viewTwo) {
        final String letter = viewOne.getText().toString();
        final Property property = new Property(viewOne);
        viewTwo.setTag(property);
        TranslateAnimation animation = new TranslateAnimation(0, viewTwo.getX() - viewOne.getX(), 0, viewTwo.getY() - viewOne.getY());
        animation.setRepeatMode(0);
        animation.setDuration(200);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewOne.setOnClickListener(null);
                viewOne.setVisibility(GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewTwo.setText(letter);
                viewTwo.setBackgroundResource(R.drawable.bg_answer_letter);
                viewTwo.setTextColor(getResources().getColor(R.color.color_black));
                // detecting final answer word.
                if (detectingFillAnswer()) {
                    boolean result = false;
                    StringBuilder choiceAnswer = new StringBuilder();
                    for (String letter : choiceAlphaAnswers) {
                        choiceAnswer.append(letter);
                    }
                    String strCompare = choiceAnswer.toString();
                    String correctAnswer = question.getCorrectAlphaAnswer();
                    if (strCompare.equalsIgnoreCase(correctAnswer)) {
                        result = true;
                        setCorrectAnswer();
                    } else {
                        result = false;
                        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                        for (TextView textView : answerViews) {
                            textView.setTextColor(getResources().getColor(R.color.error_text));
                            textView.setBackgroundResource(R.drawable.bg_error_letter);
                            textView.startAnimation(shake);
                        }
                    }
                    if (resultListener != null) {
                        resultListener.OnAnswerResult(result);
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        viewOne.startAnimation(animation);
    }

    /**
     * Move view to generate area.
     *
     * @param viewOne this view need to move.
     * @param viewTwo this view target that one moving to it.
     */
    private synchronized void moveToGenerate(final TextView viewOne, final TextView viewTwo) {
        TranslateAnimation animation = new TranslateAnimation(viewOne.getX() - viewTwo.getX(), 0,
                viewOne.getY() - viewTwo.getY(), 0);
        animation.setRepeatMode(0);
        animation.setDuration(200);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewTwo.setVisibility(VISIBLE);
                viewTwo.setOnClickListener(listener);
                viewOne.setText("");
                viewOne.setBackgroundResource(R.drawable.bg_empty_letter);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                for (Integer position : choicePositions) {
                    TextView textView = answerViews.get(position);
                    textView.setTextColor(getResources().getColor(R.color.gray));
                    textView.setBackgroundResource(R.drawable.bg_answer_letter);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        viewTwo.startAnimation(animation);
    }

    /**
     * using when you want to show correct answer. Case: finish time count for answer.
     */
    public void setCorrectAnswer() {
        synchronized (this) {
            resetEvent();

            String fontPath = getContext().getString(R.string.OpenSans_Regular);
            Typeface face = Typeface.createFromAsset(getContext().getAssets(), fontPath);
            for (int i = 0; i < question.getUnicodeChars().size(); i++) {
                if (i < answerViews.size()) {
                    String unicodeLetter = question.getUnicodeChars().get(i);
                    TextView textView = answerViews.get(i);
                    textView.setTextColor(getResources().getColor(R.color.color_green));
                    textView.setBackgroundResource(R.drawable.bg_empty_letter);
                    textView.setTypeface(face);
                    textView.setText(unicodeLetter);
                }
            }
        }
    }

    class Property {
        TextView tvMove;

        Property(TextView tvMove) {
            this.tvMove = tvMove;
        }

        TextView getTvMove() {
            return tvMove;
        }
    }

    public static class Question {
        String correctAlphaAnswer;
        final String RANDOM_CHARACTER = "bcdghklmnpqrstvxy";
        final String[] VOWEL = {"a", "e", "i", "o", "u"};
        ArrayList<String> alphaChars;
        ArrayList<String> unicodeChars;


        public Question(String correctUnicodeAnswer, String correctAlphaAnswer) {
            String strUnicode = correctUnicodeAnswer.replace(" ", "").toUpperCase();
            String strAlpha = correctAlphaAnswer.replace(" ", "").toLowerCase();
            this.correctAlphaAnswer = strAlpha;
            alphaChars = new ArrayList<>();
            unicodeChars = new ArrayList<>();
            StringBuilder unicode = new StringBuilder().append(strUnicode);
            StringBuilder alpha = new StringBuilder().append(strAlpha);
            for (int i = 0; i < unicode.length(); i++) {
                unicodeChars.add(String.valueOf(unicode.charAt(i)));
            }
            for (int i = 0; i < alpha.length(); i++) {
                alphaChars.add(String.valueOf(unicode.charAt(i)));
            }
        }

        String getCorrectAlphaAnswer() {
            return correctAlphaAnswer;
        }


        public ArrayList<String> getAlphaChars() {
            return alphaChars;
        }

        public ArrayList<String> getUnicodeChars() {
            return unicodeChars;
        }

        StringBuilder appendFullVowel(StringBuilder generateAnswer, String s) {
            if (generateAnswer.toString().contains(s)) {
                return generateAnswer;
            }
            if (generateAnswer.length() < 16) {
                generateAnswer.append(s);
            }
            return generateAnswer;
        }

        StringBuilder appendVowel(StringBuilder generateAnswer) {
            int countVowel = 0;
            for (int i = 0; i < generateAnswer.length(); i++) {
                String character = String.valueOf(generateAnswer.charAt(i));
                if (character.equalsIgnoreCase("a") || character.equalsIgnoreCase("e")
                        || character.equalsIgnoreCase("i") || character.equalsIgnoreCase("o")
                        || character.equalsIgnoreCase("u")) {
                    countVowel++;
                }
            }
            if (countVowel == 5)
                return generateAnswer;

            for (String letter : VOWEL) {
                appendFullVowel(generateAnswer, letter);
            }
            return generateAnswer;
        }

        /**
         * Keep rule from xcode. generate 16 answer characters.
         *
         * @return 16 answer characters.
         */
        List<String> makeGenerateAnswer() {
            synchronized (this) {
                List<String> list = new ArrayList<>();
                StringBuilder generateAnswer = new StringBuilder().append(correctAlphaAnswer);
                generateAnswer = appendVowel(generateAnswer);
                Random random = new Random();
                while (generateAnswer.length() < 16) {
                    int position = random.nextInt(RANDOM_CHARACTER.length());
                    generateAnswer.append(RANDOM_CHARACTER.charAt(position));
                }
                for (int i = 0; i < generateAnswer.length(); i++) {
                    String alpha = String.valueOf(generateAnswer.charAt(i));
                    list.add(alpha);
                }
                Collections.shuffle(list);
                return list;
            }
        }

    }

    private int getRowIndexOfCharacter(int characterIndex, int maxCharPerRow) {
        return 1 + characterIndex / maxCharPerRow;
    }

}
