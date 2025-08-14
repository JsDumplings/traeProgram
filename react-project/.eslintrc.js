module.exports = { 
  extends: [
    'eslint:recommended',
    'plugin:react/recommended'
  ],
  plugins: [
    'react'
  ],
  settings: { 
    'import/resolver': { 
      alias: { 
        map: [ 
          ['@utils', './src/utils'], 
          ['@', './src'] 
        ], 
        extensions: ['.js', '.jsx', '.ts', '.tsx'] 
      } 
    } 
  } ,
  parser: '@typescript-eslint/parser',
  parserOptions: { 
    ecmaVersion: 2020, 
    sourceType: 'module', 
    ecmaFeatures: { 
      jsx: true 
    } 
  }, 
};