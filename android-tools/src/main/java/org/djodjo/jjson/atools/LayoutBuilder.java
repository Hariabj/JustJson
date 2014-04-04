/*
 * Copyright (C) 2014 Kalin Maldzhanski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.djodjo.jjson.atools;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import org.djodjo.jjson.atools.ui.fragment.BooleanFragment;
import org.djodjo.jjson.atools.util.OneOfFragment;
import org.djodjo.json.LinkedTreeMap;
import org.djodjo.json.schema.Schema;
import org.djodjo.json.schema.SchemaMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LayoutBuilder<T extends Schema> {


    // private final Activity activity;
    private final FragmentManager fragmentManager;
    private final T schema;
    private boolean mergeAnyOf = false;
    private boolean mergeAllOf = false;
    private boolean mergeOneOf = false;

    //othe options are radio or other selector buttons alike
    private int typeChooser4AnyOf = FragmentBuilder.DISPLAY_TYPE_SPINNER;
    private int typeChooser4AllOf = FragmentBuilder.DISPLAY_TYPE_SPINNER;
    private int typeChooser4OneOf = FragmentBuilder.DISPLAY_TYPE_SPINNER;


    //the following controllers are used instead of a general selector
    //these are actual Json Object properties with Enum values,
    //where the name of the property is added to the list
    //the generation of the other Views will depend on the value of these
    //the they are picked in the order defined and control the following ones
    //i.e the first controller will get all possible values displayed,
    //the next one just the ones under the specific schema and sub-schemas, etc.

    private ArrayList<String> oneOfControllers = new ArrayList<String>();
    private ArrayList<String> ignoredProperties = new ArrayList<String>();

    //map for custom layouts for specific properties for this object
    private HashMap<String, Integer> customLayouts =  new HashMap<String, Integer>();
    private HashMap<String, Integer> displayTypes = new HashMap<String, Integer>();
    //TODO specific custom settings
    private HashMap<String, Integer> customButtonColors =  new HashMap<String, Integer>();
    private HashMap<String, Integer> customTitleTextAppearances = new HashMap<String, Integer>();
    private HashMap<String, Integer> customDescTextAppearances =  new HashMap<String, Integer>();
    private HashMap<String, Integer> customValueTextAppearances = new HashMap<String, Integer>();
    private HashMap<String, Boolean> showTitle =  new HashMap<String, Boolean>();
    private HashMap<String, Boolean> showDescription = new HashMap<String, Boolean>();



    //TODO global custom settings
    /**
     * a mask of the possible display types for all elements
     */
    private int globalDisplayTypes;
    private int globalButtonColor;
    private int globalTitleTextAppearance;
    private int globalDescTextAppearance;
    private int globalValuesTextAppearance;
    private boolean globalShowDescription;
    private boolean globalShowTitle;


    private LinkedTreeMap<String, FragmentBuilder> fragBuilders = new LinkedTreeMap<String, FragmentBuilder>();
    OneOfFragment oneOfFragment =  null;

    public LayoutBuilder(T schema, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.schema = schema;
    }


    public LayoutBuilder<T> addCustomButtonColor(String propertyName, Integer customButtonColor) {
        this.customButtonColors.put(propertyName, customButtonColor);
        return this;
    }

    public LayoutBuilder<T> addCustomButtonColors(HashMap<String, Integer> customButtonColors) {
        this.customButtonColors.putAll(customButtonColors);
        return this;
    }

    public LayoutBuilder<T> addCustomTitleTextAppearance(String propertyName, Integer customTitleTextAppearance) {
        this.customTitleTextAppearances.put(propertyName, customTitleTextAppearance);
        return this;
    }

    public LayoutBuilder<T> addCustomTitleTextAppearances(HashMap<String, Integer> customTitleTextAppearances) {
        this.customTitleTextAppearances.putAll(customTitleTextAppearances);
        return this;
    }

    public LayoutBuilder<T> addCustomDescTextAppearance(String propertyName, Integer customDescTextAppearance) {
        this.customDescTextAppearances.put(propertyName, customDescTextAppearance);
        return this;
    }

    public LayoutBuilder<T> addCustomDescTextAppearances(HashMap<String, Integer> customDescTextAppearances) {
        this.customDescTextAppearances.putAll(customDescTextAppearances);
        return this;
    }

    public LayoutBuilder<T> addCustomValueTextAppearance(String propertyName, Integer customValueTextAppearance) {
        this.customValueTextAppearances.put(propertyName, customValueTextAppearance);
        return this;
    }

    public LayoutBuilder<T> addCustomValueTextAppearances(HashMap<String, Integer> customValueTextAppearances) {
        this.customValueTextAppearances.putAll(customValueTextAppearances);
        return this;
    }

    public LayoutBuilder<T> setShowTitle(HashMap<String, Boolean> showTitle) {
        this.showTitle = showTitle;
        return this;
    }

    public LayoutBuilder<T> setShowDescription(HashMap<String, Boolean> showDescription) {
        this.showDescription = showDescription;
        return this;
    }

    public LayoutBuilder<T> setGlobalDisplayTypes(int globalDisplayTypes) {
        this.globalDisplayTypes = globalDisplayTypes;
        return this;
    }

    public LayoutBuilder<T> setGlobalButtonColor(int globalButtonColor) {
        this.globalButtonColor = globalButtonColor;
        return this;
    }

    public LayoutBuilder<T> setGlobalTitleTextAppearance(int globalTitleTextAppearance) {
        this.globalTitleTextAppearance = globalTitleTextAppearance;
        return this;
    }

    public LayoutBuilder<T> setGlobalDescTextAppearance(int globalDescTextAppearance) {
        this.globalDescTextAppearance = globalDescTextAppearance;
        return this;
    }

    public LayoutBuilder<T> setGlobalValuesTextAppearance(int globalValuesTextAppearance) {
        this.globalValuesTextAppearance = globalValuesTextAppearance;
        return this;
    }

    public LayoutBuilder<T> setGlobalShowDescription(boolean globalShowDescription) {
        this.globalShowDescription = globalShowDescription;
        return this;
    }

    public LayoutBuilder<T> setGlobalShowTitle(boolean globalShowTitle) {
        this.globalShowTitle = globalShowTitle;
        return this;
    }



    public LayoutBuilder<T> addOneOfController(String propertyName) {
        oneOfControllers.add(propertyName);
        return this;
    }

    public LayoutBuilder<T> addOneOfControllers(ArrayList<String> propertyNames) {
        oneOfControllers.addAll(propertyNames);
        return this;
    }

    public LayoutBuilder<T> ignoreProperty(String propertyName) {
        ignoredProperties.add(propertyName);
        return this;
    }

    public LayoutBuilder<T> ignoreProperties(ArrayList<String> propertyNames) {
        ignoredProperties.addAll(propertyNames);
        return this;
    }

    public LayoutBuilder<T> addCustomLayout (String propertyName, int layoutId) {
        customLayouts.put(propertyName, layoutId);
        return this;
    }

    public LayoutBuilder<T> addCustomLayouts (Map<String, Integer> propertyLayouts) {
        customLayouts.putAll(propertyLayouts);
        return this;
    }

    public LayoutBuilder<T> addDisplayType (String propertyName, int displayType) {
        displayTypes.put(propertyName, displayType);
        return this;
    }

    public LayoutBuilder<T> addDisplayTypes (Map<String, Integer> propertyDisplayTypes) {
        displayTypes.putAll(propertyDisplayTypes);
        return this;
    }

    public void reset() {
        fragBuilders = new LinkedTreeMap<String, FragmentBuilder>();
    }

    public void build(ViewGroup vg, boolean append) {
        build(vg.getId(), append);
    }
    public void build(ViewGroup vg) {
        build(vg.getId());
    }
    public void build(int containerId) {
        build(containerId, false);
    }
    public void build(int containerId, boolean append) {
        if(fragmentManager==null) return;
        if(fragBuilders == null || fragBuilders.size()<1)
        {

            SchemaMap schemaTopProperties = schema.getProperties();

            // --> First find basic properties
            if(schemaTopProperties!=null) {
                for (Map.Entry<String, Schema> property : schemaTopProperties) {
                    if (ignoredProperties.contains(property.getKey())) continue;
                    Schema propSchema = property.getValue();
                    FragmentBuilder fragBuilder = new FragmentBuilder(property.getKey(), propSchema);
                    fragBuilders.put(property.getKey(),
                            fragBuilder
                                    .withDisplayType(chooseDisplayType(property.getKey()))
                                    .withLayoutId(getCustomLayoutId(property.getKey()))
                    );
                }
            }

            // --> check for oneOf
            if (schema.getOneOf() != null && schema.getOneOf().getJson().length() > 0) {
                ArrayList<Schema> oneOfSchemas = schema.getOneOf().getJsonWrappersList();
                ArrayList<String> stringSchemas = new ArrayList<String>();
                for (Schema oneOfSchema : oneOfSchemas) {
                    //before sending schemas to the oneOf fragment check if they are not already defined in here. if so merge and remove from common Layout
                    if(schemaTopProperties!=null) {
                        SchemaMap propSchemas = oneOfSchema.getProperties();
                        for (Map.Entry<String, Schema> property : propSchemas) {
                            Schema topPropertySchema = schemaTopProperties.optValue(property.getKey());
                            if (topPropertySchema != null) {
                                property.getValue().merge(topPropertySchema);
                                fragBuilders.remove(property.getKey());
                            }
                        }
                    }

                    //now add the schema to send
                    stringSchemas.add(oneOfSchema.getJson().toString());
                }
                //
                oneOfFragment = OneOfFragment.newInstance(stringSchemas, oneOfControllers, displayTypes, customLayouts);
            }

        }


        // --> The TRANSACTION
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // --> clean fragments if not appending
        if(!append) {
            //FragmentTransaction.replace does not replace all the fragments in the container but only one thus we need to remove them all one by one
            Fragment currFrag =  fragmentManager.findFragmentById(containerId);
            Log.d(this.getClass().toString(), "start fragment removal");
            while(currFrag!=null) {
                try {
                    fragmentManager.beginTransaction().remove(currFrag).commit();
                    // fragment will not be removed instantly so we need to wait for the next one, otherwise too many commits buildup in the heap causing OutOfMemory
                    Fragment nextFrag =  fragmentManager.findFragmentById(containerId);
                    while (nextFrag != null && nextFrag==currFrag) {
                        nextFrag =  fragmentManager.findFragmentById(containerId);
                    }
                    currFrag = nextFrag;
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            Log.d(this.getClass().toString(), "complete fragment removal");
        }

        // --> build and add fragments
        for (Map.Entry<String, FragmentBuilder> builder:fragBuilders.entrySet()) {
            Fragment fragment = builder.getValue().build();
            if(fragment!= null) {
                transaction.add(containerId, fragment, builder.getKey());
            }

        }

        //add oneOf fragment if exists
        if(oneOfFragment != null) {
            transaction.add(containerId, oneOfFragment, "oneOf");
        }

        transaction.commit();

    }


    private int getCustomLayoutId(String propertyName) {
        int res = 0;
        if(customLayouts.containsKey(propertyName)) {
            res = customLayouts.get(propertyName);
        }
        return res;
    }

    private int chooseDisplayType(String property) {
        int res = -1;

        if(displayTypes.containsKey(property))
            res = displayTypes.get(property);

        return res;
    }

    private int chooseTitleTextAppearance(String property) {
        int res = 0;

        return res;
    }

    private int chooseDescTextAppearance(String property) {
        int res = 0;

        return res;
    }

    private int chooseValueTextAppearance(String property) {
        int res = 0;

        return res;
    }

    private int chooseButtonColor(String property) {
        int res = 0;

        return res;
    }


}
