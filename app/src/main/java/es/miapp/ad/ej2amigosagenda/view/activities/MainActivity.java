/*
 * Copyright (c) 2021. ArseneLupin0.
 *
 * Licensed under the GNU General Public License v3.0
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 *
 * Permissions of this strong copyleft license are conditioned on making available complete source
 * code of licensed works and modifications, which include larger works using a licensed work,
 * under the same license. Copyright and license notices must be preserved. Contributors provide
 * an express grant of patent rights.
 */

package es.miapp.ad.ej2amigosagenda.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import es.miapp.ad.ej2amigosagenda.R;
import es.miapp.ad.ej2amigosagenda.databinding.ActivityMainBinding;
import es.miapp.ad.ej2amigosagenda.util.Permissions;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private Permissions permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        init();
    }

    private void init() {
        permissions = new Permissions(this, this, b);

        launchingActivity();
    }

    private void launchingActivity() {
        SharedPreferences SPFirstStart = getSharedPreferences("SPFirstStart", MODE_PRIVATE);
        boolean firstStart = SPFirstStart.getBoolean("firstStart", true);

        initDrawer();

        if (firstStart) {
            firstTimePermissions();
            return;
        }

        if (permissions.hasAllPerms(permissions.getPERMISSIONS())) {
            permissions.permissionsApp();
        }
    }

    private void firstTimePermissions() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Primera vez en abrir la App")
                .setMessage("Parece que es la primera vez que abres la aplicación." +
                        "\n\nNecesita aceptar una serie de permisos para poder continuar, si no la" +
                        " aplicación no podrá hacer las operaciones correctamente.")
                .setPositiveButton("¡Vale!", (dialog, which) -> {

                    if (permissions.hasAllPerms(permissions.getPERMISSIONS())) {
                        permissions.askPermissions();
                    } else {
                        Snackbar.make(b.getRoot().getRootView(), "Tienes todos los permisos", Snackbar.LENGTH_SHORT).show();
                    }

                    dialog.dismiss();
                }).setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel())
                .create().show();

        SharedPreferences prefs = getSharedPreferences("SPFirstStart", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void initDrawer() {
        setSupportActionBar(b.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(b.drawerLayout).build();

        NavigationUI.setupWithNavController(b.toolbar, navController, appBarConfiguration);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(b.navigationViewMain, navController);

        toolbarCollab(navController);
    }

    private void toolbarCollab(NavController navController) {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            b.appBarLayout.setExpanded(false, true);
            b.appBarLayout.setNestedScrollingEnabled(false);
        });
    }
}