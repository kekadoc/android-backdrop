
package com.qegame.backdropinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.qegame.animsimple.anim.Anim;
import com.qegame.animsimple.path.RotationX;
import com.qegame.animsimple.path.RotationY;
import com.qegame.animsimple.path.RotationZ;
import com.qegame.animsimple.path.TranslationY;
import com.qegame.animsimple.path.params.AnimParams;
import com.qegame.bottomappbarqe.BottomAppBarQe;
import com.qegame.materialinterface.MaterialInterface;
import com.qegame.qeutil.graph.QeColor;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity-TAG";

    MaterialInterface materialInterface;

    private boolean horizontal;

    private View.OnClickListener icon_first = new View.OnClickListener() {
        private int count;
        @Override
        public void onClick(View v) {
            MaterialButton button = new MaterialButton(MainActivity.this);
            button.setText("This Button №" + count);
            materialInterface.addViewToBack(button, true);
            materialInterface.snack().show("Button added!");
            count++;
        }
    };
    private View.OnClickListener icon_second = new View.OnClickListener() {
        private int count;
        @Override
        public void onClick(View v) {
            materialInterface.removeViewInBack(0, true);
            materialInterface.snack().show("Button removed!");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        materialInterface = findViewById(R.id.material_interface);

        materialInterface.getBar().setConstruction(getFabCenter());
        materialInterface.setSubtitle("Subtitle");

        materialInterface.setFrontShape(MaterialInterface.FrontShape.ALL_ROUND);
        materialInterface.setContentPadding(50, 50, 50, 0);

        View view = getLayoutInflater().inflate(R.layout.content_v, materialInterface.getContentContainer(), false);
        materialInterface.setContentView(view);

        materialInterface.getBar().snack().show("Snack");
        materialInterface.getBar().progress().set(50);

        materialInterface.buildFirstIcon(new BottomAppBarQe.IconSettings() {
            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.reset);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (horizontal) {
                            horizontal = false;
                            View view = getLayoutInflater().inflate(R.layout.content_v, materialInterface.getContentContainer(), false);
                            materialInterface.setContentView(view);
                        } else {
                            horizontal = true;
                            View view = getLayoutInflater().inflate(R.layout.content_h, materialInterface.getContentContainer(), false);
                            materialInterface.setContentView(view);
                        }
                    }
                };
            }
        });
        materialInterface.buildSecondIcon(new BottomAppBarQe.IconSettings() {
            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.face);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialInterface.getBar().sheet().swich();
                    }
                };
            }
        });
        View sheet = getLayoutInflater().inflate(R.layout.qwe, materialInterface.getBar().sheet().getView(), true);
        MaterialButton button = sheet.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            private int rnd() {
                return QeColor.getRandomColor();
            }
            @Override
            public void onClick(View v) {
                materialInterface.setColor(rnd(), rnd(), rnd(), rnd(), rnd(), rnd(), rnd(), rnd(), rnd(), rnd(), rnd(), rnd(), rnd(), rnd());
            }
        });
       // materialInterface.getBar().sheet().getView().addView(new MaterialButton(this));
    }

    private BottomAppBarQe.Construction.FABEnd getFabEnd() {
        BottomAppBarQe.FABSettings fab = new BottomAppBarQe.FABSettings() {
            @Override
            public Drawable getImage() {
                return null;
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Anim<View> anim = new Anim<>(v);

                        RotationX<View> rotationX = new RotationX<>(new AnimParams.OfFloat<>(v, 0f, 360f, 1000L));
                        RotationY<View> rotationY = new RotationY<>(new AnimParams.OfFloat<>(v, 0f, 360f, 1000L));
                        RotationZ<View> rotationZ = new RotationZ<>(new AnimParams.OfFloat<>(v, 0f, 360f, 1000L));

                        TranslationY<View> translationY = new TranslationY<>(new AnimParams.OfFloat<>(v, 0f, -360f, 1000L));

                        anim.playTogether(translationY, rotationY);
                        anim.setReverse(true);
                        anim.start();

                        /*if (materialInterface.getBar().progress().getValue() <= 10)
                            materialInterface.getBar().progress().set(100);
                        materialInterface.getBar().progress().add(-10);
                        materialInterface.getBar().snack().show(String.valueOf(materialInterface.getBar().progress().getValue()));*/
                    }
                };
            }
        };

        BottomAppBarQe.IconSettings icon_0 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.add);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return icon_first;
            }
        };
        BottomAppBarQe.IconSettings icon_1 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.sub);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return icon_second;
            }
        };
        BottomAppBarQe.IconSettings icon_2 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialInterface.getBar().setConstruction(getFabCenter());
                    }
                };
            }
        };

        materialInterface.getBar().progress().show();
        materialInterface.getBar().progress().set(100f);
        return new BottomAppBarQe.Construction.FABEnd(fab, icon_0, icon_1, icon_2);
    }
    private BottomAppBarQe.Construction.FABCenter getFabCenter() {
        BottomAppBarQe.FABSettings fab = new BottomAppBarQe.FABSettings() {
            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.face);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (materialInterface.getBar().progress().getValue() >= 90)
                            materialInterface.getBar().progress().set(0);
                        materialInterface.getBar().progress().add(10);
                        materialInterface.getBar().snack().show(String.valueOf(materialInterface.getBar().progress().getValue()));
                    }
                };
            }
        };

        BottomAppBarQe.IconSettings icon_0 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.add);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return icon_first;
            }
        };
        BottomAppBarQe.IconSettings icon_1 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.sub);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return icon_second;
            }
        };
        BottomAppBarQe.IconSettings icon_2 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialInterface.getBar().setConstruction(getFabEnd());
                    }
                };
            }
        };

        materialInterface.getBar().progress().show();
        materialInterface.getBar().progress().set(0f);

        return new BottomAppBarQe.Construction.FABCenter(fab, new BottomAppBarQe.IconSettings[]{icon_0, icon_1}, new BottomAppBarQe.IconSettings[]{icon_2});
    }

}
