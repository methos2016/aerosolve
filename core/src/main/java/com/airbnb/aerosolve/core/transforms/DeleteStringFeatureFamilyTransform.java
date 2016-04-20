package com.airbnb.aerosolve.core.transforms;

import com.airbnb.aerosolve.core.FeatureVector;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.typesafe.config.Config;

/**
 * "field1" specifies the string column to be deleted
 */
public class DeleteStringFeatureFamilyTransform implements Transform {
  private String fieldName1;
  private List<String> fieldNames;

  @Override
  public void configure(Config config, String key) {
    if (config.hasPath(key + ".field1")) {
      fieldName1 = config.getString(key + ".field1");
    }
    if (config.hasPath(key + ".fields")) {
      fieldNames = config.getStringList(key + ".fields");
    }
  }

  @Override
  public void doTransform(FeatureVector featureVector) {
    Map<String, Set<String>> stringFeatures = featureVector.getStringFeatures();
    if (stringFeatures == null) {
      return ;
    }

    HashSet<String> fieldNamesSet = new HashSet<>();

    if (fieldName1 != null) {
      fieldNamesSet.add(fieldName1);
    }
    if (fieldNames != null) {
      fieldNamesSet.addAll(fieldNames);
    }

    for (String fieldName: fieldNamesSet) {
      Set<String> feature1 = stringFeatures.get(fieldName1);
      if (feature1 != null) {
        stringFeatures.remove(fieldName);
      }
    }
  }
}
