package com.qegame.materialinterface;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.qegame.bottomappbarqe.BottomAppBarQe;
import com.qegame.qeanim.AbsQeAnim;
import com.qegame.qeanim.AnimHolder;
import com.qegame.qeanim.interpolation.Interpolations;
import com.qegame.qeanim.set.LeaveMoveRotation;
import com.qegame.qeanim.view.TranslationY;
import com.qegame.qeshaper.QeShaper;
import com.qegame.qeutil.androids.QeAndroid;
import com.qegame.qeutil.androids.views.QeViews;
import com.qegame.qeutil.doing.Do;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MaterialInterface extends FrameLayout {
    private static final String TAG = "MaterialInterface-TAG";

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
    private ViewGroup content_shutter;
    private ScrollView scroll_back;

    private int colorFront;
    private int colorSubtitleRipple;

    private long durationAnimation;

    private boolean expanded;

    private MaterialShapeDrawable frontDrawable;
    private MaterialShapeDrawable subtitleDrawable;

    private AnimHolder<AbsQeAnim<ImageView>, ImageView> animNavigationBase;
    private AnimHolder<LeaveMoveRotation.Image<ImageView>, ImageView> animNavigationShow;
    private AnimHolder<LeaveMoveRotation.Image<ImageView>, ImageView> animNavigationHide;
    private AnimHolder<AbsQeAnim<View>, View> animShowBack;
    private AnimHolder<AbsQeAnim<View>, View> animHideBack;

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
    public MaterialInterface(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        inflate(context, R.layout.layout, this);

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

        int colorPrimary = a.getColor(0, 0);
        int colorOnPrimary = a.getColor(1, 0);
        int colorPrimaryDark = a.getColor(2, 0);
        int colorAccent = a.getColor(3, 0);
        int colorSurface = a.getColor(4, 0);
        int colorOnSurface = a.getColor(5, 0);
        int colorRipple = a.getColor(6, 0);

        this.colorFront = colorSurface;
        this.colorSubtitleRipple = colorRipple;

        a.recycle();

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
        this.content_shutter = findViewById(R.id.content_shutter);
        this.scroll_back = findViewById(R.id.scroll_back);

        this.scroll_back.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
               // animShowBack = null;
               // animHideBack = null;
            }
        });

        setupBackItemsPadding((int) QeAndroid.dp(context, 8));

        this.subtitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MaterialInterface.this.expanded) {
                    hideBack();
                }
            }
        });
        setSubtitle("");

        setBackColor(colorPrimary);

        int heightBABC = (int) getResources().getDimension(com.qegame.bottomappbarqe.R.dimen.height_bottom_app_bar_custom);
        int heightSubtitle = (int) getResources().getDimension(R.dimen.height_subtitle);
        int frontBottomMargin = heightBABC + heightSubtitle;

        QeViews.setMargins(this.scroll_back, 0, 0, 0, frontBottomMargin);

        this.title.setTextColor(colorOnPrimary);
        this.subtitle.setTextColor(colorOnSurface);

        setFrontShape(FrontShape.ALL_ROUND);

        this.icon_navigation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationClick();
            }
        });
        buildFirstIcon(null);
        buildSecondIcon(null);

        this.animNavigationBase = new AnimHolder.NonNullHolder<AbsQeAnim<ImageView>, ImageView>(icon_navigation) {
            @NonNull
            @Override
            protected AbsQeAnim<ImageView> initAnim(@Nullable ImageView target) {
                return TranslationY.builder(target)
                        .from((float) -target.getHeight() / 4f)
                        .to(0f)
                        .duration(1000)
                        .interpolator(Interpolations.BOUNCE)
                        .build();
            }
        };
        this.animNavigationShow = new AnimHolder.NonNullHolder<LeaveMoveRotation.Image<ImageView>, ImageView>(icon_navigation) {
            @NonNull
            @Override
            protected LeaveMoveRotation.Image<ImageView> initAnim(@Nullable ImageView target) {
                Drawable drawable = getResources().getDrawable(R.drawable.navigation_icon_close);
                return LeaveMoveRotation.Image.builder(target).image(drawable).duration(300L).build();
            }
        };
        this.animNavigationHide = new AnimHolder.NonNullHolder<LeaveMoveRotation.Image<ImageView>, ImageView>(icon_navigation) {
            @NonNull
            @Override
            protected LeaveMoveRotation.Image<ImageView> initAnim(@Nullable ImageView target) {
                Drawable drawable = getResources().getDrawable(R.drawable.navigation_icon);
                return LeaveMoveRotation.Image.builder(target).image(drawable).duration(300L).build();
            }
        };
        this.animHideBack = new AnimHolder.NonNullHolder<AbsQeAnim<View>, View>(front) {
            float val;
            @NonNull
            @Override
            protected AbsQeAnim<View> initAnim(@Nullable View target) {
                Log.e(TAG, "animHideBack: ");
                val = front.getTranslationY();
                return TranslationY.builder(target)
                        .from(front.getTranslationY())
                        .to(0f)
                        .duration(400L)
                        .interpolator(Interpolations.ANTICIPATE)
                        .build();

            }

            @Override
            protected boolean isCorrect(AbsQeAnim<View> animator, View target) {
                return super.isCorrect(animator, target) && val == front.getTranslationY();
            }
        };
        this.animShowBack = new AnimHolder.NonNullHolder<AbsQeAnim<View>, View>(front) {
            float val;
            @NonNull
            @Override
            protected AbsQeAnim<View> initAnim(@Nullable View target) {
                val = scroll_back.getHeight();
                return TranslationY.builder(target)
                        .from(0f)
                        .to((float) scroll_back.getHeight())
                        .duration(400L)
                        .interpolator(Interpolations.OVERSHOOT)
                        .build();
            }

            @Override
            protected boolean isCorrect(AbsQeAnim<View> animator, View target) {
                return super.isCorrect(animator, target) && val == scroll_back.getHeight();
            }
        };

    }

    //region Getters/Setters

    public boolean isExpanded() {
        return expanded;
    }
    public void setExpanded(boolean expanded) {
        if (expanded) showBack();
        if (!expanded) hideBack();
    }

    @NonNull
    public BottomAppBarQe getBar() {
        return bar;
    }
    @NonNull
    public ViewGroup getContentContainer() {
        return this.content;
    }
    @NonNull
    public ViewGroup getContentShutter() {
        return content_shutter;
    }

    //endregion

    public void setFrontColor(int color, int colorSubtitleText) {
        setFrontColor(color);
        subtitle.setTextColor(colorSubtitleText);
    }
    public void setFrontColor(int color) {
        frontDrawable.setTint(color);
        subtitleDrawable.setTint(color);
    }

    public void setRippleSubtitleColor(int colorRipple) {
        colorSubtitleRipple = colorRipple;
        setupSubtitleBackground(subtitleDrawable, colorRipple);
    }

    public void setBackColor(int color, int colorTitleText) {
        setBackColor(color);
        title.setTextColor(colorTitleText);
    }
    public void setBackColor(int color) {
        this.back.setBackgroundColor(color);
        //this.back_items.setBackgroundColor(color);
        //this.container_title.setBackgroundColor(color);
    }

    public void setColor(int colorFront, int subtitle, int colorRipple, int colorBack,
                         int colorPanel, int colorFab, int colorFabProgress,
                         int colorBottomSheet, int colorTitle,
                         int colorSnackBody, int colorSnackText, int colorSnackButton, int colorSnackButtonRipple, int colorSnackButtonText)
    {
        setFrontColor(colorFront, subtitle);
        setRippleSubtitleColor(colorRipple);
        setBackColor(colorBack, colorTitle);
        this.bar.setColorPanel(colorPanel, colorBottomSheet);
        this.bar.setFabColor(colorFab);
        this.bar.progress().setColor(colorFabProgress);
        this.bar.snack().setColorBody(colorSnackBody);
        this.bar.snack().setColorText(colorSnackText);
        this.bar.snack().setColorButtonBody(colorSnackButton);
        this.bar.snack().setColorButtonRipple(colorSnackButtonRipple);
        this.bar.snack().setColorButtonText(colorSnackButtonText);
    }

    public void setupBackItemsPadding(int padding) {

        back_items.setPadding(
                back_items.getPaddingLeft(),
                back_items.getPaddingTop(),
                back_items.getPaddingRight(),
                padding
        );
    }

    public void setFabEnabled(boolean enabled) {
        getBar().getFab().setEnabled(enabled);
    }

    public void setFrontShape(@NonNull FrontShape frontShape) {
        if (frontShape == FrontShape.ALL_ROUND
                || frontShape == FrontShape.LEFT_ROUND
                ||frontShape == FrontShape.RIGHT_ROUND)
        {
            this.frontDrawable = getFrontDrawableRound(frontShape);
            this.subtitleDrawable = getFrontDrawableRound(frontShape);
            setupSubtitleBackground(subtitleDrawable, colorSubtitleRipple);
            this.front.setBackground(frontDrawable);
        }

        if (frontShape == FrontShape.ALL_CUT
                || frontShape == FrontShape.LEFT_CUT
                ||frontShape == FrontShape.RIGHT_CUT)
        {
            this.frontDrawable = getFrontDrawableCut(frontShape);
            this.subtitleDrawable = getFrontDrawableCut(frontShape);
            setupSubtitleBackground(subtitleDrawable, colorSubtitleRipple);
            this.front.setBackground(frontDrawable);
        }
    }

    public void addViewToBack(@NonNull View view, final boolean reExpanded) {
        int left = (int) getResources().getDimension(R.dimen.padding_left_view_back);
        int top = (int) getResources().getDimension(R.dimen.padding_top_view_back);
        int right = (int) getResources().getDimension(R.dimen.padding_right_view_back);
        int bottom = (int) getResources().getDimension(R.dimen.padding_bottom_view_back);

        if (view.getLayoutParams() == null) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);
        }
        this.back_items.addView(view);
        QeViews.doOnMeasureView(back_items, new Do.With<LinearLayout>() {
            @Override
            public void work(LinearLayout with) {
                if (reExpanded) reExpanded();
            }
        });

    }
    public void removeViewInBack(View view, final boolean reExpanded) {
        this.back_items.removeView(view);
        QeViews.doOnMeasureView(back_items, new Do.With<LinearLayout>() {
            @Override
            public void work(LinearLayout with) {
                if (reExpanded) reExpanded();
            }
        });
    }
    public void removeViewInBack(int position, final boolean reExpanded) {
        if (this.back_items.getChildCount() == 0) return;
        this.back_items.removeViewAt(position);
        QeViews.doOnMeasureView(back_items, new Do.With<LinearLayout>() {
            @Override
            public void work(LinearLayout with) {
                if (reExpanded) reExpanded();
            }
        });
    }
    public void removeAllViewInBack(boolean reExpanded) {
        this.back_items.removeAllViews();
        QeViews.doOnMeasureView(back_items, new Do.With<LinearLayout>() {
            @Override
            public void work(LinearLayout with) {
                if (reExpanded) reExpanded();
            }
        });
    }

    @NonNull
    public LinearLayout getBackViewsContainer() {
        return back_items;
    }

    public void buildFirstIcon(@Nullable BottomAppBarQe.IconSettings iconSettings) {
        if (iconSettings == null) {
            icon_first.setVisibility(GONE);
        } else {
            icon_first.setVisibility(VISIBLE);
            icon_first.setImageDrawable(iconSettings.getImage());
            icon_first.setOnClickListener(iconSettings.getClickListener());
        }
    }
    public void buildSecondIcon(@Nullable BottomAppBarQe.IconSettings iconSettings) {
        if (iconSettings == null) {
            icon_second.setVisibility(GONE);
        } else {
            icon_second.setVisibility(VISIBLE);
            icon_second.setImageDrawable(iconSettings.getImage());
            icon_second.setOnClickListener(iconSettings.getClickListener());
        }
    }

    public void setContentView(View view) {
        this.content.removeAllViews();
        this.content.addView(view);
        if (this.expanded) hideBack();
    }

    public void setSubtitle(String title) {
        this.subtitle.setText(title);
        if (title == null || title.equals("")) this.subtitle.setVisibility(GONE);
        else this.subtitle.setVisibility(VISIBLE);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setContentPadding(int left, int top, int right, int bottom) {
        this.content_shutter.setPadding(left, top, right, bottom);
    }
    public void setContentShutterPadding(int left, int top, int right, int bottom) {
        this.content_shutter.setPadding(left, top, right, bottom);
    }

    public void performClickBottomIcon(int position) {
        this.bar.performClickIcon(position);
    }

    @NonNull
    public BottomAppBarQe.Snack snack() {
        return this.bar.snack();
    }
    @NonNull
    public BottomAppBarQe.Progress progress() {
        return this.bar.progress();
    }

    public void reExpanded() {
        if (isExpanded()) {
            if (this.back_items.getChildCount() == 0) {hideBack(); return;}
            QeViews.doOnMeasureView(this.scroll_back, new Do.With<ScrollView>() {
                @Override
                public void work(ScrollView with) {
                    TranslationY.builder(front)
                            .from(front.getTranslationY())
                            .to((float) scroll_back.getHeight())
                            .duration(durationAnimation)
                            .interpolator(Interpolations.OVERSHOOT)
                            .build().start();
                }
            });
        }
    }

    private void navigationClick() {
        if (back_items.getChildCount() == 0) {
            this.animNavigationBase.start();
            return;
        }
        if (this.expanded) hideBack();
        else showBack();
    }
    private void showBack() {
        this.animNavigationShow.start();
        this.animShowBack.start(front);
        this.expanded = true;
    }
    private void hideBack() {
        this.animNavigationHide.start();
        this.animHideBack.start();
        this.expanded = false;
    }

    private void setupSubtitleBackground(MaterialShapeDrawable drawable, int colorRipple) {
        this.subtitle.setBackground(QeShaper.injectRipple(colorRipple, drawable));
    }

    private MaterialShapeDrawable getFrontDrawableRound(FrontShape frontShape, int color) {
        ShapeAppearanceModel.Builder shape = new ShapeAppearanceModel.Builder();
        float corner = getContext().getResources().getDimension(R.dimen.corner_round);

        shape.setTopLeftCornerSize(corner);
        shape.setTopRightCornerSize(corner);

        if (frontShape == FrontShape.ALL_ROUND) {
            shape.setTopLeftCorner(new RoundedCornerTreatment());
            shape.setTopRightCorner(new RoundedCornerTreatment());
        }
        if (frontShape == FrontShape.LEFT_ROUND) {
            shape.setTopLeftCorner(new RoundedCornerTreatment());
        }
        if (frontShape == FrontShape.RIGHT_ROUND) {
            shape.setTopRightCorner(new RoundedCornerTreatment());
        }

        MaterialShapeDrawable drawable = new MaterialShapeDrawable(shape.build());
        drawable.setTint(color);
        return drawable;
    }
    private MaterialShapeDrawable getFrontDrawableRound(FrontShape frontShape) {
        return getFrontDrawableRound(frontShape, colorFront);
    }
    private MaterialShapeDrawable getFrontDrawableCut(FrontShape frontShape, int color) {
        ShapeAppearanceModel.Builder shape = new ShapeAppearanceModel.Builder();
        float corner = getContext().getResources().getDimension(R.dimen.corner_cut);

        shape.setTopLeftCornerSize(corner);
        shape.setTopRightCornerSize(corner);

        if (frontShape == FrontShape.ALL_CUT) {
            shape.setTopLeftCorner(new CutCornerTreatment());
            shape.setTopRightCorner(new CutCornerTreatment());
        }
        if (frontShape == FrontShape.LEFT_CUT) shape.setTopLeftCorner(new CutCornerTreatment());
        if (frontShape == FrontShape.RIGHT_CUT) shape.setTopRightCorner(new CutCornerTreatment());


        MaterialShapeDrawable drawable = new MaterialShapeDrawable(shape.build());
        drawable.setTint(color);
        return drawable;
    }
    private MaterialShapeDrawable getFrontDrawableCut(FrontShape frontShape) {
        return getFrontDrawableCut(frontShape, colorFront);
    }

}
