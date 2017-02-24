package com.luxoft.bankapp.exceptions;

	public class FeedException extends RuntimeException {
		static final long serialVersionUID = 1L;
        public FeedException(String message) {
             super(message);
        }
   }