package com.hendraanggrian.pages.minimal.resources

val scale_fix_js = """
    var metas = document.getElementsByTagName('meta')
    var i;

    if (navigator.userAgent.match(/iPhone/i)) {
      for (i = 0; i < metas.length; i++) {
        if (metas[i].name == 'viewport') {
          metas[i].content = 'width=device-width, minimum-scale=1.0, maximum-scale=1.0'
        }
      }
      document.addEventListener('gesturestart', gestureStart, false)
    }

    function gestureStart() {
      for (i = 0; i < metas.length; i++) {
        if (metas[i].name == 'viewport') {
          metas[i].content = 'width=device-width, minimum-scale=0.25, maximum-scale=1.6'
        }
      }
    }
""".trimIndent()

val theme_js = """
    const LOCAL_STORAGE_KEY = 'minimal-dark-mode'

    if (isDarkMode()) {
      document.getElementsByTagName('html')[0].classList.add('theme-dark')
    }

    function isDarkMode() {
      const storage = localStorage.getItem(LOCAL_STORAGE_KEY)
      return storage ? JSON.parse(storage) : false
    }

    function toggleDarkMode() {
      document.getElementsByTagName('html')[0].classList.toggle('theme-dark')
      const flippedDarkMode = !isDarkMode()
      localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(flippedDarkMode))
      return flippedDarkMode
    }
""".trimIndent()
