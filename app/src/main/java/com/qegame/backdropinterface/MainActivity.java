
package com.qegame.backdropinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.qegame.bottomappbarqe.BottomAppBarQe;
import com.qegame.materialinterface.MaterialInterface;
import com.qegame.qeutil.graph.QeColor;
import com.qegame.qeutil.listening.subscriber.Subscriber;

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

    private BottomAppBarQe.IconSettings icon_0 = new BottomAppBarQe.IconSettings() {

        @Override
        public Drawable getImage() {
            return getDrawable(R.drawable.add);
        }

        @Override
        public View.OnClickListener getClickListener() {
            return icon_first;
        }
    };
    private BottomAppBarQe.IconSettings icon_1 = new BottomAppBarQe.IconSettings() {

        @Override
        public Drawable getImage() {
            return getDrawable(R.drawable.sub);
        }

        @Override
        public View.OnClickListener getClickListener() {
            return icon_second;
        }
    };
    private BottomAppBarQe.IconSettings icon_2 = new BottomAppBarQe.IconSettings() {

        @Override
        public Drawable getImage() {
            return getDrawable(R.drawable.navigation_icon_close);
        }

        @Override
        public View.OnClickListener getClickListener() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialInterface.snack()
                            .make("Custom Snack")
                            .radius(20).buttonText("Y")
                            .corners(BottomAppBarQe.Snack.Corner.CUT)
                            .colorText(Color.BLACK)
                            .colorBody(Color.YELLOW)
                            .show();
                }
            };
        }
    };
    private BottomAppBarQe.IconSettings icon_3 = new BottomAppBarQe.IconSettings() {

        @Override
        public Drawable getImage() {
            return getDrawable(R.drawable.reset);
        }

        @Override
        public View.OnClickListener getClickListener() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (materialInterface.progress().isShown())
                        materialInterface.progress().add(10);
                    else {
                        materialInterface.progress().show(0, true);
                        materialInterface.progress().add(10);
                    }
                    materialInterface.progress().onCompletely().subscribe(new Subscriber() {
                        @Override
                        public void onCall() {
                            materialInterface.progress().remove(true);
                        }
                    });
                    materialInterface.getBar().snack().show(String.valueOf(materialInterface.getBar().progress().getValue()));
                }
            };
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
                        materialInterface.getBar().sheet().switchVisible();
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
                        materialInterface.getBar().setConstruction(getFabCenter());
                    }
                };
            }
        };

        return new BottomAppBarQe.Construction.FABEnd(fab, icon_0, icon_1, icon_2, icon_3);
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
                        materialInterface.getBar().setConstruction(getFabEnd());
                    }
                };
            }
        };

        return new BottomAppBarQe.Construction.FABCenter(fab, new BottomAppBarQe.IconSettings[]{icon_0, icon_1}, new BottomAppBarQe.IconSettings[]{icon_2, icon_3});
    }

}
