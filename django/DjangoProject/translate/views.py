from django.shortcuts import render

from django.http import HttpResponse

import transformer_nmt

def translate(request, url_data):
    ts = transformer_nmt.Translate(url_data)
    return HttpResponse(ts.translate_it(ts.userInput))
