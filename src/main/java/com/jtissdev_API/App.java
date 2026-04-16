package com.jtissdev_API;

import com.jtissdev_API.engine.loader.ComptaDataLoader;
import com.jtissdev_API.engine.loader.PcgDataLoader;
import com.jtissdev_API.features.PCG.dto.Type_Comptable;
import com.jtissdev_API.features.core.dto.PcgCoreDTO;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
/**
 * @author J.Tiss
 * @since 1.0.0
 */
public class App {

    // On peut déclarer les membres ici

    public static void main(String[] args) {
        System.out.println("Hello World!");
        comptaDataLoading();
    }

    static void comptaDataLoading(){

        PcgDataLoader pcgLoader = new PcgDataLoader();
        PcgCoreDTO pcg = pcgLoader.loadFromJson("data/PCG.json");
    }


}
