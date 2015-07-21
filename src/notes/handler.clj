(ns notes.handler
  (:require

   ; Маршруты приложения
   [notes.routes :refer [notes-routes]]

   ; Стандартные настройки middleware
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

; Обернем маршруты в middleware
(def app
  (wrap-defaults notes-routes site-defaults))
