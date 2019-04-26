package com.qegame.materialinterface;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.snackbar.Snackbar;
import com.qegame.animsimple.Anim;
import com.qegame.bottomappbarqe.BottomAppBarQe;
import com.qegame.qeshaper.QeShaper;
import com.qegame.qeutil.QeUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MaterialInterface extends FrameLayout {
    private final String TAG = "MaterialInterface-ИНФ";

    public enum FrontShape {
        ALL_ROUND,
        ALL_CUT,
        LEFT_ROUND,
        RIGHT_ROUND,
        LEFT_CUT,
        RIGHT_CUT
    }

    private ViewGroup back;
    private ViewGroup front;
    private ViewGroup container_title;
    private BottomAppBarQe bar;
    private ImageView icon_navigation;
    private ImageView icon_first;
    private ImageView icon_second;
    private TextView title;
    private TextView subtitle;
    private LinearLayout back_items;
    private ViewGroup content;
    private ScrollView scroll_back;

    private int colorPrimary;
    private int colorPrimaryDark;
    private int colorAccent;
    private int colorSurface;
    private int colorOnSurface;
    private int colorOnPrimary;
    private int colorRipple;

    private long durationAnimation;

    private boolean expanded;

    public MaterialInterface(@NonNull Context context) {
        super(context);
        init(context, null);
    }
    public MaterialInterface(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public MaterialInterface(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        inflate(context, R.layout.layout, this);

        initColors(context);

        this.durationAnimation = 400;

        this.back = findViewById(R.id.back);
        this.front = findViewById(R.id.front);
        this.bar = findViewById(R.id.bar);
        this.icon_navigation = findViewById(R.id.icon_navigation);
        this.icon_first = findViewById(R.id.icon_first);
        this.icon_second = findViewById(R.id.icon_second);
        this.title = findViewById(R.id.title);
        this.back_items = findViewById(R.id.back_items);
        this.container_title = findViewById(R.id.container_title);
        this.subtitle = findViewById(R.id.subtitle);
        this.content = findViewById(R.id.content);
        this.scroll_back = findViewById(R.id.scroll_back);

        this.subtitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MaterialInterface.this.expanded) {
                    hideBack();
                }
            }
        });
        setSubtitle("");

        setBackColor(this.colorPrimary);

        int heightBABC = (int) getResources().getDimension(com.qegame.bottomappbarqe.R.dimen.height_bottom_app_bar_custom);
        int heightSubtitle = (int) getResources().getDimension(R.dimen.height_subtitle);
        int frontBottomMargin = heightBABC + heightSubtitle;

        QeUtil.setMargin(this.scroll_back, 0, 0, 0, frontBottomMargin);

        this.title.setTextColor(this.colorOnPrimary);
        this.subtitle.setTextColor(this.colorOnSurface);

        setFrontShape(FrontShape.ALL_ROUND);

        this.icon_navigation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationClick();
            }
        });
        buildFirstIcon(null);
        buildSecondIcon(null);

    }
    private void initColors(Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data,
                new int[] {
                        R.attr.colorPrimary,
                        R.attr.colorOnPrimary,
                        R.attr.colorPrimaryDark,
                        R.attr.colorAccent,
                        R.attr.colorSurface,
                        R.attr.colorOnSurface,
                        R.attr.colorControlHighlight});

        this.colorPrimary = a.getColor(0, 0);
        this.colorOnPrimary = a.getColor(1, 0);
        this.colorPrimaryDark = a.getColor(2, 0);
        this.colorAccent = a.getColor(3, 0);
        this.colorSurface = a.getColor(4, 0);
        this.colorOnSurface = a.getColor(5, 0);
        this.colorRipple = a.getColor(6, 0);
    }

    //region Getters/Setters

    public BottomAppBarQe getBar() {
        return bar;
    }
    public ViewGroup getContentContainer() {
        return this.content;
    }
    //endregion

    public void setFrontShape(FrontShape frontShape) {
        if (frontShape == FrontShape.ALL_ROUND
                || frontShape == FrontShape.LEFT_ROUND
                ||frontShape == FrontShape.RIGHT_ROUND)
        {
            this.front.setBackground(getFrontDrawableRound(frontShape));
            this.subtitle.setBackground(QeShaper.setupRipple(this.colorRipple, getFrontDrawableRound(frontShape)));
        }

        if (frontShape == FrontShape.ALL_CUT
                || frontShape == FrontShape.LEFT_CUT
                ||frontShape == FrontShape.RIGHT_CUT)
        {

            this.front.setBackground(getFrontDrawableCut(frontShape));
            this.subtitle.setBackground(QeShaper.setupRipple(this.colorRipple, getFrontDrawableCut(frontShape)));
        }
    }

    public void addViewToBack(View view) {
        int left = (int) getResources().getDimension(R.dimen.padding_left_view_back);
        int top = (int) getResources().getDimension(R.dimen.padding_top_view_back);
        int right = (int) getResources().getDimension(R.dimen.padding_right_view_back);
        int bottom = (int) getResources().getDimension(R.dimen.padding_bottom_view_back);

        if (view.getLayoutParams() == null) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);
        }
        back_items.addView(view);
    }
    public void removeAllViewInBack() {
        back_items.removeAllViews();
    }

    public void buildFirstIcon(BottomAppBarQe.IconSettings iconSettings) {
        if (iconSettings == null) {
            icon_first.setVisibility(GONE);
        } else {
            icon_first.setVisibility(VISIBLE);
            icon_first.setImageDrawable(iconSettings.getImage());
            icon_first.setOnClickListener(iconSettings.getClickListener());
        }
    }
    public void buildSecondIcon(BottomAppBarQe.IconSettings iconSettings) {
        if (iconSettings == null) {
            icon_second.setVisibility(GONE);
        } else {
            icon_second.setVisibility(VISIBLE);
            icon_second.setImageDrawable(iconSettings.getImage());
            icon_second.setOnClickListener(iconSettings.getClickListener());
        }
    }

    public void setContentView(View view) {
        //this.content.removeAllViews();
        this.content.addView(view);
    }

    public void setSubtitle(String title) {
        this.subtitle.setText(title);
        if (title == null || title == "") {
            this.subtitle.setVisibility(GONE);
        } else {
            this.subtitle.setVisibility(VISIBLE);
        }
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setBackColor(int color) {
        this.back.setBackgroundColor(color);
        this.back_items.setBackgroundColor(color);
        this.container_title.setBackgroundColor(color);
    }

    public void setContentPadding(int left, int top, int right, int bottom) {
        this.content.setPadding(left, top, right, bottom);
    }

    public void performClickBottomIcon(int position) {
        this.bar.performClickIcon(position);
    }

    public Snackbar showSnackBar(String text) {
        return this.bar.showSnackBar(text);
    }
    public Snackbar showSnackBar(String text, int duration) {
        return this.bar.showSnackBar(text, duration);
    }

    private void navigationClick() {
        if (back_items.getChildCount() == 0) {
            Anim anim_icon = new Anim(icon_navigation);
            anim_icon.translationY(-30, 0, 1000, new BounceInterpolator());
            anim_icon.start();
            return;
        }
        if (this.expanded) {
            hideBack();
        } else {
            showBack();
        }
    }
    private void showBack() {
        Anim.animate(front).translationY(0, scroll_back.getHeight(), this.durationAnimation, new OvershootInterpolator()).start();
        Drawable drawable = getResources().getDrawable(R.drawable.navigation_icon_close);
        Anim.LeaveMoveRotation anim_icon = new Anim.LeaveMoveRotation.Image(icon_navigation, this.durationAnimation, 1, drawable);
        anim_icon.start();
        this.expanded = true;
    }
    private void hideBack() {
        Anim.animate(front).translationY(front.getTranslationY(), 0, this.durationAnimation, new AnticipateInterpolator()).start();
        Drawable drawable = getResources().getDrawable(R.drawable.navigation_icon);
        Anim.LeaveMoveRotation anim_icon = new Anim.LeaveMoveRotation.Image(icon_navigation, this.durationAnimation, 1, drawable);
        anim_icon.start();
        this.expanded = false;
    }

    private Drawable getFrontDrawableRound(FrontShape frontShape) {
        ShapeAppearanceModel shape = new ShapeAppearanceModel();
        float corner = getContext().getResources().getDimension(R.dimen.corner_round);

        if (frontShape == FrontShape.ALL_ROUND) {
            shape.setTopLeftCorner(new RoundedCornerTreatment(corner));
            shape.setTopRightCorner(new RoundedCornerTreatment(corner));
        }
        if (frontShape == FrontShape.LEFT_ROUND) {
            shape.setTopLeftCorner(new RoundedCornerTreatment(corner));
        }
        if (frontShape == FrontShape.RIGHT_ROUND) {
            shape.setTopRightCorner(new RoundedCornerTreatment(corner));
        }

        MaterialShapeDrawable drawable = new MaterialShapeDrawable(shape);
        drawable.setTint(colorSurface);
        return drawable;
    }
    private Drawable getFrontDrawableCut(FrontShape frontShape) {
        ShapeAppearanceModel shape = new ShapeAppearanceModel();
        float corner = getContext().getResources().getDimension(R.dimen.corner_cut);

        if (frontShape == FrontShape.ALL_CUT) {
            shape.setTopLeftCorner(new CutCornerTreatment(corner));
            shape.setTopRightCorner(new CutCornerTreatment(corner));
        }
        if (frontShape == FrontShape.LEFT_CUT) {
            shape.setTopLeftCorner(new CutCornerTreatment(corner));
        }
        if (frontShape == FrontShape.RIGHT_CUT) {
            shape.setTopRightCorner(new CutCornerTreatment(corner));
        }

        MaterialShapeDrawable drawable = new MaterialShapeDrawable(shape);
        drawable.setTint(colorSurface);
        return drawable;
    }
}
