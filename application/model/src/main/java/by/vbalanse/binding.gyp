{
  "targets": [
    {
      "target_name": "keypress",
      "conditions": [
	      ['OS == "mac"', {
		      'include_dirs': [
		          'System/Library/Frameworks/CoreFoundation.Framework/Headers',
		          'System/Library/Frameworks/Carbon.Framework/Headers',
		        ],
		      "link_settings": {
	                            "libraries": [
	                                "-framework Carbon",
	                                "-framework CoreFoundation"
	                            ]
	                        }
	            }
	          ]
	        ],
	      "sources": [ "keypress.cc" ]
    }
  ]
}