from django.urls import path, include

import translate.views

urlpatterns = [
    path('translate', translate.views.translate)
]