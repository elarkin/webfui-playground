(ns webfui-playground.core
  (:use [webfui.framework :only [launch-app]])
  (:use-macros [webfui.framework.macros :only [add-mouse-watch]]))

(def ball-pos
  [525 395])

(def ball-orientation
  0)

(defn adj [x]
  (- x 50))


(defn center [style]
  (-> style
      (update-in [:top] adj)
      (update-in [:left] adj)))

(defn render-all [foot-style]
  (let [[left top] ball-pos
        ball-style {:top top
                    :left left
                    :-webkit-transform (str "rotate(" ball-orientation "deg)")}]
    [:div {:mouse :mouse}
     [:span#ball {:style (center ball-style)}]
     [:span#foot {:mouse :mouse
                  :style (center foot-style)}]]))

(defn abs [n]
  (.abs js/Math n))

(defn bounded [position]
  (let [[x y] position]
    [(-> x
         (max 50)
         (min 974))
     (-> y
         (max 50)
         (min 718))]))

(defn new-ball-pos-given-diff [current-pos diff]
  (let [real-magnitude (->> diff
                            (map abs)
                            (apply +))
        adj-magnitude (max 100 (- 200 real-magnitude))
        proportions (map #(/ % real-magnitude) diff)
        adj-diff (map #(* % adj-magnitude) proportions)]
    (bounded (map + current-pos adj-diff))))

(defn move-ball [foot-pos ball-pos]
  (let [diff (map - ball-pos foot-pos)
        [x y] diff]
    (if (and (< (abs x) 100) (< (abs y) 100))
      (new-ball-pos-given-diff ball-pos diff)
      ball-pos)))

(defn new-ball-orientation [ball-orientation old-ball new-ball]
  (if (= old-ball new-ball)
    ball-orientation
    (rand-int 180)))

(add-mouse-watch :mouse [state first-element last-element points]
                 (let [foot-pos  (last points)
                       new-ball (move-ball foot-pos ball-pos)
                       new-orientation (new-ball-orientation ball-orientation ball-pos new-ball)
                       [foot-x foot-y] foot-pos]
                   (do
                     (def ball-pos new-ball)
                     (def ball-orientation new-orientation)
                     {:top foot-y :left foot-x})))

(def state
  (atom {:left 60
         :top 400}))

(launch-app state render-all)
