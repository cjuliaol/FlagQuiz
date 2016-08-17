package com.cjuliaolahoz.flagquiz;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//import java.util.logging.Handler;

/**
 * A placeholder fragment containing a simple view.
 */
// Contains the Flag Quiz Logic
public class MainActivityFragment extends Fragment {

    // String used when logging error messages

    private static final String TAG = "FlagQuiz Activity";
    private static final int FLAGS_IN_QUIZ = 10;

    private List<String> fileNameList;  // flag file names
    private List<String> quizCountriesList; // countries in current quiz
    private Set<String> regionsSet; // regions in current quiz
    private String correctAnswer; // correct country for current flag
    private int totalGuesses; // number of guesses made
    private int correctAnswers; // number of correct guesses
    private int guessRows; // number of rows displaying guess Buttons
    private SecureRandom random; // used to randomize the quiz
    private Handler handler; // Used to delay loading new flag
    private Animation shakeAnimation; // animation for incorrect guess

    private LinearLayout quizLinearLayout; // layout that contains the quiz
    private TextView questionNumberTextView; // shows current question #
    private ImageView flagImageView; // displays a flag
    private LinearLayout[] guessLinearLayouts; // rows of answer buttons
    private TextView answerTextView; // displays correct answer

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.fragment_main, container, false);
        // Initialization
        fileNameList = new ArrayList<>(); // diamong operator
        quizCountriesList = new ArrayList<>();
        random = new SecureRandom();
        handler = new Handler(); // from android.os.Handler

        // load the shake animation that's used for incorrect answers
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.incorrect_shake);
        shakeAnimation.setRepeatCount(3);

        // get references to GUI components
        quizLinearLayout = (LinearLayout) view.findViewById(R.id.quizLinearLayout);
        questionNumberTextView = (TextView) view.findViewById(R.id.questionNumberTextView);
        flagImageView = (ImageView) view.findViewById(R.id.flagImageView);
        guessLinearLayouts = new LinearLayout[4];
        guessLinearLayouts[0] = (LinearLayout) view.findViewById(R.id.row1LinearLayout);
        guessLinearLayouts[1] = (LinearLayout) view.findViewById(R.id.row2LinearLayout);
        guessLinearLayouts[2] = (LinearLayout) view.findViewById(R.id.row3LinearLayout);
        guessLinearLayouts[3] = (LinearLayout) view.findViewById(R.id.row4LinearLayout);
        answerTextView = (TextView) view.findViewById(R.id.answerTextView);

        // configure listeners for the guess buttons
        for ( LinearLayout row: guessLinearLayouts) {
            for(int column=0; column< row.getChildCount(); column++) {
                Button button = (Button) row.getChildAt(column);
             //   button.setOnClickListener(guessButtonListener);
            }
        }

        // set questionNumberTextView's text
        questionNumberTextView.setText(getString(R.string.question,1,FLAGS_IN_QUIZ));

        return view;
    }

    public void updateGuessRows(SharedPreferences defaultSharedPreferences) {
      // get the number of guess buttons that should be display
        String choices = defaultSharedPreferences.getString( MainActivity.CHOICES,null);
        guessRows = Integer.parseInt(choices);


        // hide all guess buttons in LinearLayouts
        for ( LinearLayout layout:guessLinearLayouts){
            layout.setVisibility(View.GONE);
        }
        // display appropiate guess buttons LinearLayouts
        for ( int row =0; row < guessRows; row++) {
            guessLinearLayouts[row].setVisibility(View.VISIBLE);
        }

    }

    public void updateRegions(SharedPreferences defaultSharedPreferences) {
      regionsSet =
              defaultSharedPreferences.getStringSet(MainActivity.REGIONS,null);
    }

    public void resetQuiz() {

    }
}
