package inducesmile.com.androidstaggeredgridlayoutmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Random;


public class MoreInfoFragment extends Fragment {

    public static final Random RANDOM = new Random();
    private Button rollDices;
    private ImageView imageView1, imageView2, imageView3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.simple_games, container, false);

        rollDices = view.findViewById(R.id.rollDices);
        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);

        rollDices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation anim1 = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                final Animation anim2 = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                final Animation anim3 = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int value = randomDiceValue();
                        int res = getResources().getIdentifier("dice_" + value, "mipmap", "inducesmile.com.androidstaggeredgridlayoutmanager");

                        if (animation == anim1) {
                            imageView1.setImageResource(res);
                        } else if (animation == anim2) {
                            imageView2.setImageResource(res);
                        } else if (animation == anim3){
                            imageView3.setImageResource(res);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };

                anim1.setAnimationListener(animationListener);
                anim2.setAnimationListener(animationListener);
                anim3.setAnimationListener(animationListener);

                imageView1.startAnimation(anim1);
                imageView2.startAnimation(anim2);
                imageView3.startAnimation(anim3);
            }
        });

        return view;
    }

    public static int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }





}

