package com.example.network

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import java.io.ByteArrayInputStream
import java.io.InputStream
import javax.xml.parsers.SAXParserFactory

class SaxActivity : AppCompatActivity() {
    var mResult: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sax)
        mResult = findViewById<View>(R.id.result) as TextView
    }

    fun click(v: View) {
        val xml = """<?xml version="1.0" encoding="utf-8"?>
<root><team>Kia</team><team>Samsung</team></root>"""
        try {
            val factory =
                SAXParserFactory.newInstance()
            val parser = factory.newSAXParser()
            val reader = parser.xmlReader
            val handler = SaxHandler()
            reader.contentHandler = handler
            val istream: InputStream =
                ByteArrayInputStream(xml.toByteArray(charset("utf-8")))
            reader.parse(InputSource(istream))
            mResult!!.text = "팀 명: " + handler.item
        } catch (e: Exception) {
            Toast.makeText(v.context, e.message, 0).show()
        }
    }

    internal inner class SaxHandler : DefaultHandler() {
        var initem = false
        var item = StringBuilder()
        override fun startDocument() {}
        override fun endDocument() {}
        override fun startElement(
            uri: String,
            localName: String,
            qName: String,
            atts: Attributes
        ) {
            if (localName == "team") {
                initem = true
            }
        }

        override fun endElement(
            uri: String,
            localName: String,
            qName: String
        ) {
        }

        override fun characters(chars: CharArray, start: Int, length: Int) {
            if (initem) {
                item.append(chars, start, length)
                item.append(":")
                initem = false
            }
        }
    }
}