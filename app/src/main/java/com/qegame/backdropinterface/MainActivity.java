
package com.qegame.backdropinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qegame.animsimple.Anim;
import com.qegame.bottomappbarqe.BottomAppBarQe;
import com.qegame.materialinterface.MaterialInterface;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity-ИНФ";

    MaterialInterface materialInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        materialInterface = findViewById(R.id.material_interface);

        materialInterface.getBar().setConstruction(getFabCenter());

        materialInterface.setSubtitle("Subtitle");

        /*materialInterface.removeAllViewInBack();
        for (int i = 0; i < 10; i++) {
            materialInterface.addViewToBack(new Button(this));
        }

        materialInterface.buildFirstIcon(new BottomAppBarCustom.IconSettings() {
            @Override
            public Drawable getImage() {
                return getResources().getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new ToastListener("Icon first clicked!");
            }
        });

        materialInterface.setFrontShape(MaterialInterface.FrontShape.ALL_ROUND);*/
        materialInterface.setContentPadding(50, 50, 50, 0);


        View view = getLayoutInflater().inflate(R.layout.content, materialInterface.getContentContainer(), false);
        materialInterface.setContentView(view);
        materialInterface.performClickBottomIcon(1);

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

            @Override
            public Anim getAnimation(Anim animDefault) {
                return animDefault;
            }
        };

        BottomAppBarQe.IconSettings icon_0 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new ToastListener("icon_0");
            }
        };
        BottomAppBarQe.IconSettings icon_1 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new ToastListener("icon_1");
            }
        };
        BottomAppBarQe.IconSettings icon_2 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new ToastListener("icon_2");
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
                        materialInterface.getBar().setConstruction(getFabEnd());
                    }
                };
            }

            @Override
            public Anim getAnimation(Anim animDefault) {
                return animDefault;
            }
        };

        BottomAppBarQe.IconSettings icon_0 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                Log.e(TAG, "getClickListener: ");
                return new ToastListener("icon_0");
            }
        };
        BottomAppBarQe.IconSettings icon_1 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new ToastListener("icon_1");
            }
        };
        BottomAppBarQe.IconSettings icon_2 = new BottomAppBarQe.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new ToastListener("icon_2");
            }
        };

        return new BottomAppBarQe.Construction.FABCenter(fab, new BottomAppBarQe.IconSettings[]{icon_0, icon_1}, new BottomAppBarQe.IconSettings[]{icon_2});
    }

    private class ToastListener implements View.OnClickListener {
        String text;
        public ToastListener(String text) {
            this.text = text;
        }

        @Override
        public void onClick(View v) {
            Log.e(TAG, "onClick: ");
            materialInterface.getBar().showSnackBar(text);
        }
    }
    
}
