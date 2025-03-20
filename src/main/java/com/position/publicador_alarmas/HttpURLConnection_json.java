/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.position.publicador_alarmas;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author egatica
 */
public class HttpURLConnection_json {

    public String detete(final String url, final String parameters) {
        try {
            final byte[] bytes = parameters.getBytes();
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            c.setRequestMethod("DELETE");
            c.setRequestProperty("Content-length", "" + bytes.length);
            c.connect();
            final OutputStream out = c.getOutputStream();
            out.write(bytes);
            out.flush();
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    return "ok";
                }
                case 201 -> {
                    return "error";
                }
            }
        } catch (IOException ex) {
            return "error";
        }
        return null;
    }
    
    public String put(final String url, final String parameters) {
        try {
            final byte[] bytes = parameters.getBytes();
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            c.setRequestMethod("PUT");
            c.setRequestProperty("Content-length", "" + bytes.length);
            c.connect();
            final OutputStream out = c.getOutputStream();
            out.write(bytes);
            out.flush();
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    return "ok";
                }
                case 201 -> {
                    return "error";
                }
                case 500 -> {
                    /*final InputStream errorstream = c.getErrorStream();
                    BufferedReader br = null;
                    if (errorstream == null) {
                        final InputStream inputstream = c.getInputStream();
                        br = new BufferedReader(new InputStreamReader(inputstream));
                    }
                    else {
                        br = new BufferedReader(new InputStreamReader(errorstream));
                    }
                    String response = "";
                    String nachricht;
                    while ((nachricht = br.readLine()) != null) {
                        response += nachricht;
                    }
                    System.out.println(response);*/
                    return "error";
                }
            }
        } catch (IOException ex) {
            return "error";
        }
        return null;
    }
    
    public String post_event_esp(final String url, final String parameters) {
        
        try {
            final byte[] bytes = parameters.getBytes();
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            c.setRequestMethod("POST");
            c.setRequestProperty("Content-length", "" + bytes.length);
            c.connect();
            final OutputStream out = c.getOutputStream();
            out.write(bytes);
            out.flush();
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    final StringBuilder sb;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()))) {
                        sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                    }
                    return sb.toString();
                }

                case 201 -> {
                    return "error";
                }
            }
        } catch (IOException ex) {
            return "error";
        }
        return null;
    }
    
    public String post(final String url, final Registros_insert parameters) {
        try {
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);
            c.setChunkedStreamingMode(0);
            c.setRequestProperty("Content-Type", "application/json");
            c.setRequestProperty("Accept", "application/json");
            c.setRequestMethod("POST");
            c.connect();
            final OutputStream outputStream = new BufferedOutputStream(c.getOutputStream());
            try (JsonWriter writer = new JsonWriter((Writer)new OutputStreamWriter(outputStream, "UTF-8"))) {
                final Gson gson = new Gson();
                gson.toJson((Object)parameters, (Type)Registros_insert.class, writer);
                writer.flush();
            }
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    return "ok";
                }
                case 201 -> {
                    return "error";
                }
                case 500 -> {
                    /*final InputStream errorstream = c.getErrorStream();
                    BufferedReader br = null;
                    if (errorstream == null) {
                        final InputStream inputstream = c.getInputStream();
                        br = new BufferedReader(new InputStreamReader(inputstream));
                    }
                    else {
                        br = new BufferedReader(new InputStreamReader(errorstream));
                    }
                    String response = "";
                    String nachricht;
                    while ((nachricht = br.readLine()) != null) {
                        response += nachricht;
                    }
                    System.out.println(response);*/
                    return "error";
                }
            }
        } catch (JsonIOException | IOException ex) {
            return "error";
        }
        return null;
    }
    
    public String post_alm_gps(final String url, final Registros_alm_gps_insert parameters) {
        try {
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);
            c.setChunkedStreamingMode(0);
            c.setRequestProperty("Content-Type", "application/json");
            c.setRequestProperty("Accept", "application/json");
            c.setRequestMethod("POST");
            c.connect();
            final OutputStream outputStream = new BufferedOutputStream(c.getOutputStream());
            try (JsonWriter writer = new JsonWriter((Writer)new OutputStreamWriter(outputStream, "UTF-8"))) {
                final Gson gson = new Gson();
                gson.toJson((Object)parameters, (Type)Registros_alm_gps_insert.class, writer);
                writer.flush();
            }
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    final InputStream errorstream = c.getErrorStream();
                    BufferedReader br;
                    if (errorstream == null) {
                        final InputStream inputstream = c.getInputStream();
                        br = new BufferedReader(new InputStreamReader(inputstream));
                    }
                    else {
                        br = new BufferedReader(new InputStreamReader(errorstream));
                    }
                    String response = "";
                    String nachricht;
                    while ((nachricht = br.readLine()) != null) {
                        response += nachricht;
                    }
                    return response;
                }
                case 201 -> {
                    return "error";
                }
                case 500 -> {
                    /*final InputStream errorstream2 = c.getErrorStream();
                    BufferedReader br3;
                    if (errorstream2 == null) {
                        final InputStream inputstream2 = c.getInputStream();
                        br3 = new BufferedReader(new InputStreamReader(inputstream2));
                    }
                    else {
                        br3 = new BufferedReader(new InputStreamReader(errorstream2));
                    }
                    String response2 = "";
                    String nachricht2;
                    while ((nachricht2 = br3.readLine()) != null) {
                        response2 += nachricht2;
                    }
                    System.out.println(response2);*/
                    return "error";
                }
            }
        } catch (JsonIOException | IOException ex) {
            return "error";
        }
        return null;
    }
    
    public String post_alm(final String url, final Registros_alm_insert parameters) {
        try {
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);
            c.setChunkedStreamingMode(0);
            c.setRequestProperty("Content-Type", "application/json");
            c.setRequestProperty("Accept", "application/json");
            c.setRequestMethod("POST");
            c.connect();
            final OutputStream outputStream = new BufferedOutputStream(c.getOutputStream());
            try (JsonWriter writer = new JsonWriter((Writer)new OutputStreamWriter(outputStream, "UTF-8"))) {
                final Gson gson = new Gson();
                gson.toJson((Object)parameters, (Type)Registros_alm_insert.class, writer);
                writer.flush();
            }
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    return "ok";
                }
                case 201 -> {
                    return "error";
                }
                case 500 -> {
                    /*final InputStream errorstream = c.getErrorStream();
                    BufferedReader br = null;
                    if (errorstream == null) {
                        final InputStream inputstream = c.getInputStream();
                        br = new BufferedReader(new InputStreamReader(inputstream));
                    }
                    else {
                        br = new BufferedReader(new InputStreamReader(errorstream));
                    }
                    String response = "";
                    String nachricht;
                    while ((nachricht = br.readLine()) != null) {
                        response += nachricht;
                    }
                    System.out.println(response);*/
                    return "error";
                }
            }
        } catch (JsonIOException | IOException ex) {
            return "error";
        }
        return null;
    }
    
    public String post_alm_vel(final String url, final Registros_alm_insert_vel parameters) {
        try {
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);
            c.setChunkedStreamingMode(0);
            c.setRequestProperty("Content-Type", "application/json");
            c.setRequestProperty("Accept", "application/json");
            c.setRequestMethod("POST");
            c.connect();
            final OutputStream outputStream = new BufferedOutputStream(c.getOutputStream());
            try (JsonWriter writer = new JsonWriter((Writer)new OutputStreamWriter(outputStream, "UTF-8"))) {
                final Gson gson = new Gson();
                gson.toJson((Object)parameters, (Type)Registros_alm_insert_vel.class, writer);
                writer.flush();
            }
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    return "ok";
                }
                case 201 -> {
                    /*final InputStream errorstream201 = c.getErrorStream();
                    BufferedReader br201 = null;
                    if (errorstream201 == null) {
                        final InputStream inputstream = c.getInputStream();
                        br201 = new BufferedReader(new InputStreamReader(inputstream));
                    }
                    else {
                        br201 = new BufferedReader(new InputStreamReader(errorstream201));
                    }
                    String response201 = "";
                    String nachricht201;
                    while ((nachricht201 = br201.readLine()) != null) {
                        response201 += nachricht201;
                    }
                    System.out.println(response201);*/
                    return "error";
                }
                case 500 -> {
                    /*final InputStream errorstream202 = c.getErrorStream();
                    BufferedReader br202 = null;
                    if (errorstream202 == null) {
                        final InputStream inputstream2 = c.getInputStream();
                        br202 = new BufferedReader(new InputStreamReader(inputstream2));
                    }
                    else {
                        br202 = new BufferedReader(new InputStreamReader(errorstream202));
                    }
                    String response202 = "";
                    String nachricht202;
                    while ((nachricht202 = br202.readLine()) != null) {
                        response202 += nachricht202;
                    }
                    System.out.println(response202);*/
                    return "error";
                }
            }
        } catch (JsonIOException | IOException ex) {
            return "error";
        }
        return null;
    }
    
    public String post_event_esp_alm(final String url, final Registros_eventos_especiales_alm parameters) {
        try {
            final URL u = new URL(url);
            //System.out.println("post_event_esp_alm POST=>" + url + "<=");
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);
            c.setChunkedStreamingMode(0);
            c.setRequestProperty("Content-Type", "application/json");
            c.setRequestProperty("Accept", "application/json");
            c.setRequestMethod("POST");
            c.connect();
            final OutputStream outputStream = new BufferedOutputStream(c.getOutputStream());
            try (JsonWriter writer = new JsonWriter((Writer)new OutputStreamWriter(outputStream, "UTF-8"))) {
                final Gson gson = new Gson();
                gson.toJson((Object)parameters, (Type)Registros_eventos_especiales_alm.class, writer);
                writer.flush();
            }
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    final InputStream okstream = c.getErrorStream();
                    BufferedReader okbr;
                    if (okstream == null) {
                        final InputStream inputstream = c.getInputStream();
                        okbr = new BufferedReader(new InputStreamReader(inputstream));
                    }
                    else {
                        okbr = new BufferedReader(new InputStreamReader(okstream));
                    }
                    String okresponse = "";
                    String oknachricht;
                    while ((oknachricht = okbr.readLine()) != null) {
                        okresponse += oknachricht;
                    }
                    return okresponse.replaceAll("\"", "");
                }
                case 201 -> {
                    /*final InputStream errorstream201 = c.getErrorStream();
                    BufferedReader br201 = null;
                    if (errorstream201 == null) {
                        final InputStream inputstream2 = c.getInputStream();
                        br201 = new BufferedReader(new InputStreamReader(inputstream2));
                    }
                    else {
                        br201 = new BufferedReader(new InputStreamReader(errorstream201));
                    }
                    String response201 = "";
                    String nachricht201;
                    while ((nachricht201 = br201.readLine()) != null) {
                        response201 += nachricht201;
                    }
                    System.out.println(response201);*/
                    return "error";
                }
                case 500 -> {
                    /*final InputStream errorstream202 = c.getErrorStream();
                    BufferedReader br202 = null;
                    if (errorstream202 == null) {
                        final InputStream inputstream3 = c.getInputStream();
                        br202 = new BufferedReader(new InputStreamReader(inputstream3));
                    }
                    else {
                        br202 = new BufferedReader(new InputStreamReader(errorstream202));
                    }
                    String response202 = "";
                    String nachricht202;
                    while ((nachricht202 = br202.readLine()) != null) {
                        response202 += nachricht202;
                    }
                    System.out.println(response202);*/
                    return "error";
                }
            }
        } catch (JsonIOException | IOException ex) {
            return "error";
        }
        return null;
    }
    
    public String get(final String url, final int timeout) {
        HttpURLConnection c = null;
        try {
            final URL u = new URL(url);
            c = (HttpURLConnection)u.openConnection();
            c.setRequestProperty("Content-Type", "application/json;odata=verbose");
            c.setRequestProperty("Accept", "application/json;odata=verbose");
            c.setRequestMethod("GET");
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    final StringBuilder sb;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()))) {
                        sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                    }
                    //System.out.println("GET:" + sb.toString().trim());
                    return sb.toString();
                }

                case 201 -> {
                    /*final InputStream errorstream201 = c.getErrorStream();
                    BufferedReader brerror201 = null;
                    if (errorstream201 == null) {
                        final InputStream inputstream201 = c.getInputStream();
                        brerror201 = new BufferedReader(new InputStreamReader(inputstream201));
                    }
                    else {
                        brerror201 = new BufferedReader(new InputStreamReader(errorstream201));
                    }
                    String response201 = "";
                    String nachricht201;
                    while ((nachricht201 = brerror201.readLine()) != null) {
                        response201 += nachricht201;
                    }
                    System.out.println(response201);*/
                    return null;
                }
                case 500 -> {
                    /*final InputStream errorstream202 = c.getErrorStream();
                    BufferedReader brerror202 = null;
                    if (errorstream202 == null) {
                        final InputStream inputstream202 = c.getInputStream();
                        brerror202 = new BufferedReader(new InputStreamReader(inputstream202));
                    }
                    else {
                        brerror202 = new BufferedReader(new InputStreamReader(errorstream202));
                    }
                    String response202 = "";
                    String nachricht202;
                    while ((nachricht202 = brerror202.readLine()) != null) {
                        response202 += nachricht202;
                    }
                    System.out.println(response202);*/
                    return "error";
                }
                default ->  {
                }
            }
        /*}
        catch (MalformedURLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex2) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex2);
        }*/
        } catch (IOException ex) {
            return "error";
        }
        finally {
            if (c != null) {
                try {
                    c.disconnect();
                }
                catch (Exception ex3) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex3);
                }
            }
        }
        return null;
    }
}
