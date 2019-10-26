
package com.qegame.backdropinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.qegame.bottomappbarqe.BottomAppBarQe;
import com.qegame.materialinterface.MaterialInterface;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity-TAG";

    MaterialInterface materialInterface;

    private boolean horizontal;

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


        materialInterface.addViewToBack(new Button(MainActivity.this), false);
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
                            View view = getLayoutInflater().inflate(R.layout.content_v, materialInterface.getContentContainer(), false);
                            materialInterface.setContentView(view);
                        } else {
                            View view = getLayoutInflater().inflate(R.layout.content_h, materialInterface.getContentContainer(), false);
                            materialInterface.setContentView(view);
                        }
                    }
                };
            }
        });

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
                        materialInterface.getBar().snack().show("");
                        if (materialInterface.getBar().progress().getValue() >= 90)
                            materialInterface.getBar().progress().set(0);
                        materialInterface.getBar().progress().add(10);
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
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialInterface.addViewToBack(new Button(MainActivity.this), true);
                        materialInterface.snack().show("Button added!");
                    }
                };
            }
        };
        BottomAppBarQe.IconSettings icon_1 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.sub);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialInterface.removeViewInBack(0, true);
                        materialInterface.snack().show("Button removed!");
                    }
                };
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

        return new BottomAppBarQe.Construction.FABEnd(fab, icon_0, icon_1, icon_2);
    }
    private BottomAppBarQe.Construction.FABCenter getFabCenter() {
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
                        materialInterface.getBar().snack().show("");
                        if (materialInterface.getBar().progress().getValue() >= 90)
                            materialInterface.getBar().progress().set(0);
                        materialInterface.getBar().progress().add(10);
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
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialInterface.addViewToBack(new Button(MainActivity.this), true);
                        materialInterface.snack().show("Button added!");
                    }
                };
            }
        };
        BottomAppBarQe.IconSettings icon_1 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.sub);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialInterface.removeViewInBack(0, true);
                        materialInterface.snack().show("Button removed!");
                    }
                };
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

        return new BottomAppBarQe.Construction.FABCenter(fab, new BottomAppBarQe.IconSettings[]{icon_0, icon_1}, new BottomAppBarQe.IconSettings[]{icon_2});
    }
}
