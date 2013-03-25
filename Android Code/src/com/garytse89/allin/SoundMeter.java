/*
 * Copyright (C) 2008 Google Inc.
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

package com.garytse89.allin;


import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;

public class SoundMeter {
        static final private double EMA_FILTER = 0.6;

        private MediaRecorder mRecorder = null;
        private double mEMA = 0.0;

        public void start()  {
                if (mRecorder == null) {
                        mRecorder = new MediaRecorder();
                        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mRecorder.setOutputFile("/dev/null/");
                   
                   try {
					mRecorder.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    mRecorder.start();
                    mEMA = 0.0;
                }
        }
        
        public void stop() {
                if (mRecorder != null) {
                        mRecorder.stop();       
                        mRecorder.release();
                        mRecorder = null;
                }
        }
        
        public double getTheAmplitude(){
        	if(mRecorder != null)
        		return (mRecorder.getMaxAmplitude());
        	else
        		return 1;
        }
        public double getAmplitude() {
                if (mRecorder != null)
                        return  (mRecorder.getMaxAmplitude()/2700.0);
                else
                        return 0;

        }

        public double getAmplitudeEMA() {
                double amp = getAmplitude();
                mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
                return mEMA;
        }
}