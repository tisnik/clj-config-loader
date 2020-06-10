;
;  (C) Copyright 2017, 2020  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns config-loader.config-loader-test
  (:require [clojure.test :refer :all]
            [config-loader.config-loader :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
  "Test if given function-name is bound to the real function."
  [function-name]
  (clojure.test/function? function-name))

;
; Tests for functions existence
;
(deftest test-properties->map-existence
  "Check that the config-loader.config-loader/properties->map definition exists."
  (testing
    "if the config-loader.config-loader/properties->map definition exists."
    (is (callable? 'config-loader.config-loader/properties->map))))


(deftest test-load-property-file-existence
  "Check that the config-loader.config-loader/load-property-file definition exists."
  (testing
    "if the config-loader.config-loader/load-property-file definition exists."
    (is (callable? 'config-loader.config-loader/load-property-file))))


(deftest test-load-configuration-file-existence
  "Check that the config-loader.config-loader/load-configuration-file definition exists."
  (testing
    "if the config-loader.config-loader/load-configuration-file definition exists."
    (is (callable? 'config-loader.config-loader/load-configuration-file))))

;
; Tests for functions behaviour
;
(deftest test-properties->map
  "Check the function properties->map."
  (testing
    "how properties are converted into map."
    (let [empty-property (new java.util.Properties)]
      (is (= (properties->map empty-property) {}) "empty property"))
    (let [property-with-one-item (new java.util.Properties)]
      (.setProperty property-with-one-item "foo" "bar")
      (is (= (properties->map property-with-one-item) {:foo "bar"})
          "property with one item"))
    (let [property-with-two-items (new java.util.Properties)]
      (.setProperty property-with-two-items "foo" "bar")
      (.setProperty property-with-two-items "x" "y")
      (is (= (properties->map property-with-two-items) {:foo "bar", :x "y"})
          "property with two items"))))

(deftest test-load-property-file
  "Check the function load-property-file."
  (testing
    "how properties are read and converted into map."
    (let [empty-property (load-property-file "test/empty.properties")]
      (is (= (properties->map empty-property) {}) "empty property"))
    (let [property-with-one-item (load-property-file
                                   "test/one-item.properties")]
      (is (= (properties->map property-with-one-item) {:foo "bar"})
          "property with one item"))
    (let [property-with-two-items (load-property-file
                                    "test/two-items.properties")]
      (is (= (properties->map property-with-two-items) {:foo "bar", :x "y"})
          "property with two items"))))

(deftest test-load-configuration-file
  "Check the function load-configuration-file."
  (testing
    "how properties are read and converted into map."
    (let [empty-property (load-configuration-file "test/empty.ini")]
      (is (= (properties->map empty-property) {}) "empty configuration file"))
    (let [property-with-one-item (load-configuration-file "test/one-item.ini")]
      (is (= (properties->map property-with-one-item) {:foo "bar"})
          "configuration file with one item"))
    (let [property-with-two-items (load-configuration-file
                                    "test/two-items.ini")]
      (is (= (properties->map property-with-two-items) {:foo "bar", :x "y"})
          "configuration file with two items"))))
